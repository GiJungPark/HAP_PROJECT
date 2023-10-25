package me.gijung.HAP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import me.gijung.HAP.dto.UserDto;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import me.gijung.HAP.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser("USER")
    void singUp() throws Exception {

        // given
        String email = "gijung@gmail.com";
        String password = "1234";
        String name = "박기중";
        String nickname = "기중";
        String phone = "010-0000-0000";
        String url = "/api/v1/user";
        String success_message = "회원가입에 성공하였습니다.";

        when(userService.singUp(any()))
                .thenReturn(success_message);

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestSingUp(email, password, name, nickname, phone))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(success_message);
    }

    @Test
    @DisplayName("회원가입 실패 - email 중복")
    @WithMockUser("USER")
    void singUp_fail_email_duplicated() throws Exception {

        // given
        String email = "gijung@gmail.com";
        String password = "1234";
        String name = "박기중";
        String nickname = "기중";
        String phone = "010-0000-0000";
        String url = "/api/v1/user";

        when(userService.singUp(any()))
                .thenThrow(new AppException(ErrorCode.EMAIL_DUPLICATED));

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestSingUp(email, password, name, nickname, phone))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.EMAIL_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("회원가입 실패 - phone 중복")
    @WithMockUser("USER")
    void singUp_fail_phone_duplicated() throws Exception {

        // given
        String email = "gijung@gmail.com";
        String password = "1234";
        String name = "박기중";
        String nickname = "기중";
        String phone = "010-0000-0000";
        String url = "/api/v1/user";

        when(userService.singUp(any()))
                .thenThrow(new AppException(ErrorCode.PHONE_DUPLICATED));

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestSingUp(email, password, name, nickname, phone))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.PHONE_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("회원가입 실패 - nickname 중복")
    @WithMockUser("USER")
    void singUp_fail_nickname_duplicated() throws Exception {

        // given
        String email = "gijung@gmail.com";
        String password = "1234";
        String name = "박기중";
        String nickname = "기중";
        String phone = "010-0000-0000";
        String url = "/api/v1/user";

        when(userService.singUp(any()))
                .thenThrow(new AppException(ErrorCode.NICKNAME_DUPLICATED));

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestSingUp(email, password, name, nickname, phone))))
                .andDo(print())
                .andReturn();


        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.NICKNAME_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser("USER")
    void login() throws Exception {

        // given
        String url = "/api/v1/user/login";
        String email = "email1@gmail.com";
        String password = "123";
        String token = "token";

        when(userService.login(any()))
                .thenReturn(token);

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestLogin(email, password))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.token");

        assertThat(message).isEqualTo(token);
    }

    @Test
    @DisplayName("로그인 실패 - 올바르지 않은 아이디")
    @WithMockUser("USER")
    void login_fail_not_found_email() throws Exception {

        // given
        String url = "/api/v1/user/login";
        String email = "email1@gmail.com";
        String password = "123";

        when(userService.login(any()))
                .thenThrow(new AppException(ErrorCode.EMAIL_NOTFOUND));

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestLogin(email, password))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.EMAIL_NOTFOUND.getMessage());

    }

    @Test
    @DisplayName("로그인 실패 - 올바르지 않은 비밀번호")
    @WithMockUser("USER")
    void login_fail_invalid_password() throws Exception {

        // given
        String url = "/api/v1/user/login";
        String email = "email1@gmail.com";
        String password = "123";

        when(userService.login(any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD));

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestLogin(email, password))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.INVALID_PASSWORD.getMessage());
    }
}
