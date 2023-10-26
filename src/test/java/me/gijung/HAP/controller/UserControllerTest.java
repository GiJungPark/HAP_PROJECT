package me.gijung.HAP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import me.gijung.HAP.dto.UserDto;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import me.gijung.HAP.service.MailAuthenticationService;
import me.gijung.HAP.service.MailService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    MailService mailService;

    @MockBean
    MailAuthenticationService mailAuthenticationService;

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


    @Test
    @DisplayName("아이디 찾기 성공")
    @WithMockUser("USER")
    void find_email() throws Exception {

        // given
        String url = "/api/v1/user/email";
        String email = "email";

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("name", "박기중");
        info.add("phone", "010-0000-0000");

        when(userService.findEmail(any(), any()))
                .thenReturn(email);

        // when
        MvcResult result = mockMvc.perform(get(url).with(csrf())
                        .params(info))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.email");

        assertThat(message).isEqualTo(email);
    }

    @Test
    @DisplayName("아이디 찾기 실패 - 해당 전화번호로 가입된 정보가 없음")
    @WithMockUser("USER")
    void find_email_fail_not_found_phone() throws Exception {

        // given
        String url = "/api/v1/user/email";

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("name", "박기중");
        info.add("phone", "010-0000-0000");

        when(userService.findEmail(any(), any()))
                .thenThrow(new AppException(ErrorCode.PHONE_NOTFOUND));

        // when
        MvcResult result = mockMvc.perform(get(url).with(csrf())
                        .params(info))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.PHONE_NOTFOUND.getMessage());
    }

    @Test
    @DisplayName("아이디 찾기 실패 - 올바르지 않은 이름")
    @WithMockUser("USER")
    void find_email_fail_invalid_name() throws Exception {

        // given
        String url = "/api/v1/user/email";

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("name", "박기중");
        info.add("phone", "010-0000-0000");

        when(userService.findEmail(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_NAME));

        // when
        MvcResult result = mockMvc.perform(get(url).with(csrf())
                        .params(info))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.INVALID_NAME.getMessage());
    }


    @Test
    @DisplayName("비밀번호 변경 성공")
    @WithMockUser("USER")
    void change_password() throws Exception {

        // given
        String url = "/api/v1/user/password";
        String email = "email";
        String password = "password";
        String success_message = "비밀번호가 변경되었습니다.";

        when(userService.changePassword(any(), any()))
                .thenReturn(success_message);

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestChangePassword(email, password))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(success_message);
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 해당 이메일로 가입된 정보가 없음")
    @WithMockUser("USER")
    void change_password_fail_not_found_phone() throws Exception {

        // given
        String url = "/api/v1/user/password";
        String email = "email";
        String password = "password";

        when(userService.changePassword(any(), any()))
                .thenReturn(ErrorCode.EMAIL_NOTFOUND.getMessage());

        // when
        MvcResult result = mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto.RequestChangePassword(email, password))))
                .andDo(print())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        String message = JsonPath.parse(response).read("$.message");

        assertThat(message).isEqualTo(ErrorCode.EMAIL_NOTFOUND.getMessage());
    }

}
