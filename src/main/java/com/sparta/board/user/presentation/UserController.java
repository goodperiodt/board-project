package com.sparta.board.user.presentation;

import com.sparta.board.user.jwt.JwtUtil;
import com.sparta.board.user.application.UserServiceImpl;
import com.sparta.board.user.presentation.dto.request.SignUpRequest;
import com.sparta.board.user.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic="UserController")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest request) {
        log.info("POST /sign-up");
        String email = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(email);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal UserDetailsImpl user,
                                       HttpServletRequest request) {
        log.info("GET /me, id(user객체를 얻어서 id얻기): {}", user.getUser().getId());
        log.info("GET /me, email: {}", user.getUsername());
        log.info("GET /me, nickname: {}", user.getUser().getNickname());
        log.info("GET /me, id: {}", user.getId());
        log.info("------------------------------");
        log.info("token: {}", request.getHeader(JwtUtil.AUTHORIZATION_HEADER));
        return null;
    }
}
