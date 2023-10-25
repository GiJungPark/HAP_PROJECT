package me.gijung.HAP.service;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailAuthenticationService {

    private final RedisService redisService;


    private static final String AUTH_CODE_PREFIX = "AuthCode ";


    public String verifiedCode(String email, String authCode) {

        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        if(!redisService.checkExistsValue(redisAuthCode) || !redisAuthCode.equals(authCode)) {
            throw new AppException(ErrorCode.INVALID_EMAIL_AUTH_CODE);
        }

        return "인증에 성공했습니다.";
    }

    public String authCode() {
        return this.createCode();
    }

    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder code = new StringBuilder();
            for(int i = 0; i < length; i++) {
                code.append(random.nextInt(10));
            }
            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new AppException(ErrorCode.NO_SUCH_ALGORITHM);
        }
    }

}
