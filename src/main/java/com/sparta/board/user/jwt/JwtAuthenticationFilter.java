package com.sparta.board.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.board.user.domain.Role;
import com.sparta.board.user.jwt.vo.AuthenticatedUserInfo;
import com.sparta.board.user.presentation.dto.request.LoginRequest;
import com.sparta.board.user.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/sign-in");
    }
    
    private LoginRequest parseLoginRequest(HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getInputStream(), LoginRequest.class);
    }

    private UsernamePasswordAuthenticationToken createAuthToken(LoginRequest dto) {
        return new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword(),
                null
        );
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest dto = parseLoginRequest(request);
            return getAuthenticationManager()
                    .authenticate(createAuthToken(dto));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private AuthenticatedUserInfo extractUseDetails(Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        Role role = ((UserDetailsImpl) authResult.getPrincipal()).getRole();
        return new AuthenticatedUserInfo(username, role);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        AuthenticatedUserInfo authenticatedUserInfo = extractUseDetails(authResult);
        String accessToken = jwtUtil.createAccessToken(authenticatedUserInfo);
        jwtUtil.loginResponse(accessToken, response);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
