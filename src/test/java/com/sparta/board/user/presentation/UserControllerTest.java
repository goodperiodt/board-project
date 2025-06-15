package com.sparta.board.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.board.user.domain.Role;
import com.sparta.board.user.presentation.dto.request.LoginRequest;
import com.sparta.board.user.presentation.dto.request.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class test {
        @Test
        @DisplayName("회원가입 api 테스트")
        @Order(1)
        void test1() throws Exception {
            // given
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .email("test1@naver.com")
                    .nickname("test1")
                    .password("test1")
                    .profileImageUrl("test1.png")
                    .role(Role.MEMBER)
                    .build();
            // when
            ResultActions result = mockMvc.perform(post("/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest))
            );
            // then
            result.andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("access 토큰이 header의 Authorization에 담겨 클라이언트에 전달되어야 한다.")
        @Order(2)
        void test2() throws Exception{
            // given
            LoginRequest loginRequest = LoginRequest.builder()
                    .email("test1@naver.com")
                    .password("test1")
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post("/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest))
            );

            // then
            resultActions.andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
        }
    }
}