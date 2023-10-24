package HAP.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.gijung.HAP.domain.User;
import me.gijung.HAP.dto.UserDto;
import me.gijung.HAP.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    void singUp() throws Exception {
        String email = "weeds2577@gmail.com";
        String password = "1234";
        String name = "박기중";
        String nickname = "기중";
        String phone = "010-0000-0000";

        mockMvc.perform(post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserDto.RequestSingUp(
                        email, password, name, nickname, phone
                ))))

                .andDo(print())
                .andExpect(status().isOk());
    }
}
