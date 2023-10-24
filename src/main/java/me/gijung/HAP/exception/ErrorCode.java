package me.gijung.HAP.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "해당 이메일은 이미 사용중입니다."),
    PHONE_DUPLICATED(HttpStatus.CONFLICT, "해당 전화번호는 이미 사용중입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "해당 닉네임은 이미 사용중입니다."),

    EMAIL_NOTFOUND(HttpStatus.CONFLICT, "해당 이메일로 가입된 정보가 존재하지 않습니다."),
    PHONE_NOTFOUND(HttpStatus.CONFLICT, "해당 전화번호로 가입된 정보가 존재하지 않습니다."),
    NAME_NOTFOUND(HttpStatus.CONFLICT, "해당 이름으로 가입된 정보가 존재하지 않습니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
    INVALID_EMAIL_AUTH_CODE(HttpStatus.UNAUTHORIZED, ""),

    UNABLE_TO_SEND_EMAIL(HttpStatus.CONFLICT, ""),

    NO_SUCH_ALGORITHM(HttpStatus.CONFLICT, ""),
    FORM_WRONG(HttpStatus.CONFLICT, "")
    ;

    private HttpStatus httpStatus;
    private String message;
}
