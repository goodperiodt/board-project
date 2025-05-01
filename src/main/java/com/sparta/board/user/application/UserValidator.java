package com.sparta.board.user.application;

import com.sparta.board.user.domain.UserRepository;
import com.sparta.board.user.exception.UserErrorCode;
import com.sparta.board.user.exception.UserException;
import com.sparta.board.user.presentation.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

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
}
