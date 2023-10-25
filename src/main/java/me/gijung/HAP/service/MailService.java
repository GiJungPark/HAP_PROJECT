package me.gijung.HAP.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gijung.HAP.domain.Mail;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private final RedisService redisService;

    private final UserService userService;

    private final MailAuthenticationService mailAuthenticationService;


    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;


    public void sendMail(Mail mail) {
        SimpleMailMessage mailForm = createMailForm(mail);
        try{
            mailSender.send(mailForm);
        } catch (RuntimeException e) {
            throw new AppException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }
    }

    private SimpleMailMessage createMailForm(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getToMail());
        message.setSubject(mail.getTitle());
        message.setText(mail.getContent());

        return message;
    }

    public String sendCodeToEmail(String toMail) {

        String title = "HAP 계정 메일 인증";
        String authCode = mailAuthenticationService.authCode();
        String context = "아래의 인증번호를 복사하여 이메일 인증을 완료해주세요. \n" + authCode
                + "\n인증번호는 10분 동안만 유효합니다.";

        Mail mail = Mail.builder()
                .toMail(toMail)
                .title(title)
                .content(context)
                .build();

        sendMail(mail);

        redisService.setValues(AUTH_CODE_PREFIX + toMail,
                authCode, Duration.ofMillis(authCodeExpirationMillis));

        return "인증번호가 전송되었습니다.";
    }

}
