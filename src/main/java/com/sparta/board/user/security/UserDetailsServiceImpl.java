package com.sparta.board.user.security;

import com.sparta.board.user.domain.UserRepository;
import com.sparta.board.user.exception.UserErrorCode;
import com.sparta.board.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername() 메서드 호출");
        log.info("email: {}, 로그인 시도", username);
        return userRepository.findByEmail(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(()->new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}
