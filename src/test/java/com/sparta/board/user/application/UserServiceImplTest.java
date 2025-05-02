package com.sparta.board.user.application;

import com.sparta.board.user.domain.Role;
import com.sparta.board.user.domain.User;
import com.sparta.board.user.domain.UserRepository;
import com.sparta.board.user.exception.UserException;
import com.sparta.board.user.presentation.dto.request.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.board.user.exception.UserErrorCode.DUPLICATED_EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceImplTest {
    @Mock
    private UserValidator userValidator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    UserServiceImpl userServiceImpl;

    SignUpRequest request;

    @BeforeEach
    void init() {
        userServiceImpl = new UserServiceImpl(
                userValidator,
                userRepository,
                passwordEncoder
        );

        request = SignUpRequest.builder()
                .email("test1@naver.com")
                .profileImageUrl("basic.png")
                .nickname("test1")
                .password("test1")
                .role(Role.MASTER)
                .build();
    }

    @Test
    @DisplayName("회원가입 성공시 유저의 이메일을 반환한다.")
    @Transactional
    // @Order(1)
    void test1(){
        // given
        doNothing()
                .when(userValidator)
                .validateUserSignUpRequest(request);

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encoded_test1");

        User user = request.toEntity("encoded_test1");
        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        // when
        String userEmail = userServiceImpl.signUp(request);

        // then
        assertEquals("test1@naver.com", userEmail);
    }

    @Test
    @DisplayName("이메일 중복 예외가 발생한다.")
    @Transactional
    void test2() {
        // given
        doThrow(new UserException(DUPLICATED_EMAIL))
                .when(userValidator)
                .validateUserSignUpRequest(request);

        // when & then
        UserException e = assertThrows(
                UserException.class
                ,() -> userServiceImpl.signUp(request)
        );

        assertEquals("이미 존재하는 이메일입니다.", e.getMessage());
    }
}
