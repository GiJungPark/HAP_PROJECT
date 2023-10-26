package me.gijung.HAP.service;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.domain.User;
import me.gijung.HAP.dto.UserDto;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import me.gijung.HAP.repository.UserRepository;
import me.gijung.HAP.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final RedisService redisService;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Value("${spring.data.redis.jwt-key}")
    private String redisKey;


    // 회원가입, 로그인, 로그아웃, 삭제 로직
    public String singUp(UserDto.RequestSingUp request) {

        checkDuplicateEmail(request.getEmail());

        checkDuplicateNickname(request.getNickname());

        checkDuplicatePhone(request.getPhone());

        User user = User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .nickname(request.getNickname())
                .phone(request.getPhone())
                .build();
        userRepository.save(user);

        return "SUCCESS";
    }

    public String login(UserDto.RequestLogin request) {

        User selectedUser = checkNotFoundUser(request.getEmail());

        checkPassword(selectedUser, request.getPassword());

        String token = jwtUtil.createToken(selectedUser.getEmail());


        long millisecondsUntilExpiration = jwtUtil.getExpiration(token).getTime() - new Date().getTime();

        Duration durationUntilExpiration = Duration.ofMillis(millisecondsUntilExpiration);

        redisService.setValues(redisKey + selectedUser.getEmail(), token, durationUntilExpiration);

        return token;
    }

    public String logout() {

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (redisService.checkExistsValue(redisKey + email)) {
            redisService.deleteValues(redisKey + email);
        }

        return "SUCCESS";
    }

    public String delete() {
        // 토큰의 사용자 정보 확인

        // 사용자 삭제

        // redis 토큰 삭제

        return "SUCCESS";
    }


    // 찾기, 변경 로직
    public String findEmail(String name, String phone) {

        User selectedUser = userRepository.findByPhone(phone)
                .orElseThrow(() -> new AppException(ErrorCode.PHONE_NOTFOUND));

        if(!selectedUser.getName().equals(name)){
            throw new AppException(ErrorCode.INVALID_NAME);
        }

        return selectedUser.getEmail();
    }

    public String changePassword(String email, String password) {

        User selectedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOTFOUND));

        selectedUser.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(selectedUser);

        return "비밀번호가 변경되었습니다.";
    }


    // 중복 체크 로직
    public String checkDuplicateEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.EMAIL_DUPLICATED);
                });

        return "사용가능한 이메일입니다.";
    }

    public String checkDuplicateNickname(String nickname) {
        userRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.NICKNAME_DUPLICATED);
                });

        return "사용가능한 닉네임입니다.";
    }

    public String checkDuplicatePhone(String phone) {
        userRepository.findByPhone(phone)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.PHONE_DUPLICATED);
                });

        return "사용가능한 전화번호입니다.";
    }


    public User checkNotFoundUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOTFOUND));

        return user;
    }

    public void checkPassword(User user, String password) {
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
