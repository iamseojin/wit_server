package com.arom.with_travel.global.security.token.provider;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.exception.BaseException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static com.arom.with_travel.global.exception.error.ErrorCode.EXPIRED_ACCESS_TOKEN;
import static com.arom.with_travel.global.exception.error.ErrorCode.INVALID_TOKEN;
import static com.arom.with_travel.global.security.token.properties.JwtProperties.ACCESS_TOKEN_EXPIRE_TIME;
import static com.arom.with_travel.global.security.token.properties.JwtProperties.REFRESH_TOKEN_EXPIRE_TIME;

@Getter
@Component
@Slf4j
public class JwtProvider{

    private final SecretKey SECRET_KEY;
    private final String ISS;

    public JwtProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.issuer}") String issuer
    ) {
        byte[] keyBytes = Base64.getDecoder()
                .decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.ISS = issuer;
    }

    public String generateAccessToken(Member member) {
        String token = Jwts.builder()
                .claim("type", "access")
                .issuedAt(new Date())
                .issuer(ISS)
                .audience()
                    .add(member.getEmail())
                    .add(String.valueOf(member.getId()))
                    .add(member.getRole().name()).and()
                .expiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();
        log.info("[generateAccessToken] {}", token);
        return token;
    }

    public String generateRefreshToken(Member member) {
        String token = Jwts.builder()
                .claim("type", "refresh")
                .issuedAt(new Date())
                .issuer(ISS)
                .audience()
                .add(String.valueOf(member.getId())).and()
                .expiration(new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();
        log.info("[generateRefreshToken] {}", token);
        return token;
    }

    public String parseAudience(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            if (claims.getPayload()
                    .getExpiration()
                    .before(new Date())) {
                throw BaseException.from(EXPIRED_ACCESS_TOKEN);
            }
            return claims.getPayload()
                    .getAudience()
                    .iterator()
                    .next();
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("[parseAudience] {} :{}", INVALID_TOKEN, token);
            throw BaseException.from(INVALID_TOKEN);
        } catch (BaseException e) {
            log.warn("[parseAudience] {} :{}", EXPIRED_ACCESS_TOKEN, token);
            throw BaseException.from(EXPIRED_ACCESS_TOKEN);
        }
    }

    public boolean isRefreshTokenExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            Claims body = claims.getPayload();
            String type = body.get("type", String.class);

            if (!"refresh".equals(type)) {
                throw BaseException.from(INVALID_TOKEN); // 타입이 refresh가 아닐 경우
            }

            return body.getExpiration().before(new Date());
        } catch (JwtException e) {
            throw BaseException.from(INVALID_TOKEN); // 구조가 잘못되었거나 서명 불일치
        }
    }
}
