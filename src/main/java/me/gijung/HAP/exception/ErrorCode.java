package me.gijung.HAP.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(), "파라미터 값을 확인해주세요."),

    // 404 NOT_FOUND 잘못된 리소스 접근
    EMAIL_NOTFOUND(HttpStatus.NOT_FOUND.value(), "해당 이메일로 가입된 정보가 존재하지 않습니다."),
    PHONE_NOTFOUND(HttpStatus.NOT_FOUND.value(), "해당 전화번호로 가입된 정보가 존재하지 않습니다."),
    NAME_NOTFOUND(HttpStatus.NOT_FOUND.value(), "해당 이름으로 가입된 정보가 존재하지 않습니다."),

    // 409 CONFLICT 중복된 리소스
    EMAIL_DUPLICATED(HttpStatus.CONFLICT.value(), "해당 이메일은 이미 사용중입니다."),
    PHONE_DUPLICATED(HttpStatus.CONFLICT.value(), "해당 전화번호는 이미 사용중입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT.value(), "해당 닉네임은 이미 사용중입니다."),


    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "비밀번호를 잘못 입력하였습니다."),
    INVALID_NAME(HttpStatus.UNAUTHORIZED.value(), "이름을 잘못 입력하였습니다."),
    INVALID_EMAIL_AUTH_CODE(HttpStatus.UNAUTHORIZED.value(), ""),

    UNABLE_TO_SEND_EMAIL(HttpStatus.CONFLICT.value(), ""),

    NO_SUCH_ALGORITHM(HttpStatus.CONFLICT.value(), ""),
    FORM_WRONG(HttpStatus.CONFLICT.value(), "")
    ;

    private final int httpStatus;
    private final String message;
}
