package me.gijung.HAP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestSingUp {
        private String email;
        private String password;
        private String name;
        private String nickname;
        private String phone;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestLogin {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestChangePassword {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseMessage {
        private HttpStatus status;
        private String message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseToken {
        private HttpStatus status;
        private String token;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseEmail {
        private HttpStatus status;
        private String email;
    }

}
