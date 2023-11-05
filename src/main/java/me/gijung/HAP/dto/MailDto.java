package me.gijung.HAP.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MailDto {

    private String toMail;
    private String title;
    private String content;

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
        private HttpStatus status;
        private String message;
    }
}
