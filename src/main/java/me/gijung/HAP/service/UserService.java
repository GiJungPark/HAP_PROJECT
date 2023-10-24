package me.gijung.HAP.service;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.domain.User;
import me.gijung.HAP.dto.UserDto;
import me.gijung.HAP.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입, 로그인, 로그아웃, 삭제 로직
    public String singUp(UserDto.RequestSingup request) {
        // 이메일 중복 체크 로직

        // 닉네임 중복 체크 로직

        // 전화번호 중복 체크 로직

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .nickname(request.getNickname())
                .phone(request.getPhone())
                .build();
        userRepository.save(user);

        return "SUCCESS";
    }

    public String login(UserDto.RequestLogin request) {
        // 사용자 정보 확인

        // 토큰 생성

        // 토큰 redis 저장

        return "token";
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
        // 사용자 정보 확인

        return "SUCCESS";
    }

    public String checkDuplicateNickname(String nickname) {
        // 사용자 정보 확인

        return "SUCCESS";
    }

    public String checkDuplicatePhone(String phone) {
        // 사용자 정보 확인

        return "SUCCESS";
    }
}
