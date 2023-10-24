package me.gijung.HAP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RequestSingup {
        private String email;
        private String password;
        private String name;
        private String nickname;
        private String phone;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RequestLogin {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseMessage {
        private HttpStatus httpStatus;
        private String message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseToken {
        private HttpStatus httpStatus;
        private String token;
    }
}
