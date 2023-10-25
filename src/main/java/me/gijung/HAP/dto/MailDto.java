package me.gijung.HAP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class MailDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestVerification {
        private String email;
        private String authCode;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseMessage {
        private HttpStatus httpStatus;
        private String message;
    }
}
