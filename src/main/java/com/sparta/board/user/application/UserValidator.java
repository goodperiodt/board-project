package com.sparta.board.user.application;

import com.sparta.board.user.domain.User;
import com.sparta.board.user.domain.UserRepository;
import com.sparta.board.user.exception.UserErrorCode;
import com.sparta.board.user.exception.UserException;
import com.sparta.board.user.presentation.dto.request.LoginRequest;
import com.sparta.board.user.presentation.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateUserSignUpRequest(SignUpRequest request) {
        checkDuplicateEmail(request.getEmail());
        checkDuplicateNickname(request.getNickname());
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new UserException(UserErrorCode.DUPLICATED_EMAIL);
    }

    private void checkDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname))
            throw new UserException(UserErrorCode.DUPLICATED_NICKNAME);
    }

    public User validateUserLoginRequest(LoginRequest request) {
        User user = getByEmailOrThrow(request.getEmail());
        matchesPassword(request.getPassword(),
                user.getPassword()
        );
        return user;
    }

    private void matchesPassword(String rawPassword, String encodedPassword) {
            if(!passwordEncoder.matches(rawPassword, encodedPassword))
                throw new UserException(UserErrorCode.INVALID_PASSWORD);
    }

    private User getByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}
