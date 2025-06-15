package com.sparta.board.user.application;

import com.sparta.board.user.domain.Role;
import com.sparta.board.user.jwt.JwtUtil;
import com.sparta.board.user.jwt.vo.AuthenticatedUserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("jwt 토큰 생성")
    void test1() {
        String username = "ilypsj";
        Role role = Role.MEMBER;
        AuthenticatedUserInfo authenticatedUserInfo = new AuthenticatedUserInfo(username, role);

        String token = jwtUtil.createAccessToken(authenticatedUserInfo);
        System.out.println("token: "+token);
    }
}