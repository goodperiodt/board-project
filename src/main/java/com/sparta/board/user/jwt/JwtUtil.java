package com.sparta.board.user.jwt;

import com.sparta.board.user.jwt.vo.AuthenticatedUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Getter
public class JwtUtil {
    private final SecretKey secretKey;
    private final String secretKeyPlain;
    private final long ACCESS_TOKEN_EXPIRATION=1000*60*30;
    private final String PREFIX_BEARER="Bearer ";
    private final String AUTHORIZATION_ROLE = "role";
    public static final String AUTHORIZATION_HEADER="Authorization";

    public JwtUtil(@Value("${jwt.secretKey}") String secretKeyPlain) {
        this.secretKeyPlain = secretKeyPlain;
        secretKey = Keys.hmacShaKeyFor(
                this.secretKeyPlain.getBytes(UTF_8)
        );
    }

    public String generateToken(AuthenticatedUserInfo userInfo, long expirationTime) {
        Date date = new Date();

        return Jwts.builder()
                .subject(userInfo.username()) // email
                .claim(AUTHORIZATION_ROLE, userInfo.role().name())
                .expiration(new Date(date.getTime()+expirationTime))
                .issuedAt(date)
                .signWith(secretKey)
                .compact();
    }

    public String createAccessToken(AuthenticatedUserInfo userInfo) {
        return PREFIX_BEARER+generateToken(
                userInfo,
                ACCESS_TOKEN_EXPIRATION);
    }

    public void loginResponse(String token, HttpServletResponse response) {
        response.setHeader(AUTHORIZATION_HEADER, token);
    }

    /**
     * HttpServletRequest 에서 Header 의 Authorization 데이터 얻기
     * @param request - (클라이언트) 요청 객체
     * @return "Bearer " 제외한 순수 토큰 값 또는 null
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * parseSignedClaims() 는 서명과(signature) 유효기간 검증을 자동적으로 수행한다.
     * 단, parseSignedClaims() 기능을 갖는 객체의 설정에 SecretKey 타입 필드가 설정되어 있어야 한다.
     * → 토큰 만료, 서명 위조, 그 외 JWT 검증시 발생가능한 예외 처리 작업 필요
     */
    public Jws<Claims> validateTokenAndGetClaims(String token) {
        return jwtParser().parseSignedClaims(token);
    }

    /**
     * 클라이언트로부터 전달받은 JWT 검증 가능한 객체 생성 메서드
     * → JWT 서명 위조 및 유효기간 만료 검증
     * @return JwtParser
     */
    public JwtParser jwtParser() {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build();
    }

    public Claims getUserInfo(String token) {
        return validateTokenAndGetClaims(token)
                .getPayload();
    }
}