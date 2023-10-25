package me.gijung.HAP.service;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.domain.User;
import me.gijung.HAP.dto.UserDto;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import me.gijung.HAP.repository.UserRepository;
import me.gijung.HAP.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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

        // 토큰 redis 저장

        return token;
    }

    public String logout() {
        // 토큰의 사용자 정보 확인

        // redis 토큰 삭제

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
        // 사용자 정보 확인

        return "email";
    }

    public String changePassword(String email, String password) {
        // 사용자 정보 확인

        // 비밀번호 저장

        return "SUCCESS";
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
