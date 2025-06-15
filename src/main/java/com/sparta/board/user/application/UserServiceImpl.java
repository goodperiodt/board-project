package com.sparta.board.user.application;

import com.sparta.board.user.domain.User;
import com.sparta.board.user.domain.UserRepository;
import com.sparta.board.user.presentation.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String signUp(SignUpRequest request) {
        userValidator.validateUserSignUpRequest(request);
        String encodedPassword = encodePassword(request.getPassword());

        User user = request.toEntity(encodedPassword);
        User saved = userRepository.save(user);
        return saved.getEmail();
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
