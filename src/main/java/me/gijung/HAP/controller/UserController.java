package me.gijung.HAP.controller;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.dto.UserDto;
import me.gijung.HAP.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<UserDto.ResponseMessage> singUp(@RequestBody UserDto.RequestSingUp request) {
        String message = userService.singUp(request);
        UserDto.ResponseMessage response = new UserDto.ResponseMessage(HttpStatus.OK, message);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto.ResponseMessage> checkDuplicateEmail(@RequestParam("email") String email) {
        String message = userService.checkDuplicateEmail(email);
        UserDto.ResponseMessage response = new UserDto.ResponseMessage(HttpStatus.OK, message);

        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/nickname")
    public ResponseEntity<UserDto.ResponseMessage> checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        String message = userService.checkDuplicateNickname(nickname);
        UserDto.ResponseMessage response = new UserDto.ResponseMessage(HttpStatus.OK, message);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/phone")
    public ResponseEntity<UserDto.ResponseMessage> checkDuplicatePhone(@RequestParam("phone") String phone) {
        String message = userService.checkDuplicatePhone(phone);
        UserDto.ResponseMessage response = new UserDto.ResponseMessage(HttpStatus.OK, message);

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<UserDto.ResponseToken> login(@RequestBody UserDto.RequestLogin request) {
        String token = userService.login(request);
        UserDto.ResponseToken response = new UserDto.ResponseToken(HttpStatus.OK, token);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<UserDto.ResponseMessage> logout() {
        String message = userService.logout();
        UserDto.ResponseMessage response = new UserDto.ResponseMessage(HttpStatus.OK, message);

        return ResponseEntity.ok().body(response);
    }

}
