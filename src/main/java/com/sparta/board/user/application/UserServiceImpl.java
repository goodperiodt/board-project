package com.sparta.board.user.application;

import com.sparta.board.common.exception.DuplicateAccountException;
import com.sparta.board.common.exception.DuplicateNickNameException;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String signUp(SignUpRequest request) {
        checkDuplicateAccount(request);
        checkDuplicateNickName(request);
        User user = request.toEntity(encodePassword(request));
        User saved = userRepository.save(user);
        return saved.getAccount();
    }

    private void checkDuplicateAccount(SignUpRequest request) {
        String account = request.account();
        if(userRepository.existsByAccount(account))
            throw new DuplicateAccountException("이미 존재하는 계정입니다.");
    }

    private void checkDuplicateNickName(SignUpRequest request) {
        String nickName = request.nickName();
        if(userRepository.existsByNickName(nickName))
            throw new DuplicateNickNameException("이미 존재하는 닉네임입니다.");
    }

    private String encodePassword(SignUpRequest request) {
        String rawPassword = request.password();
        return passwordEncoder.encode(rawPassword);
    }

}
