//package com.arom.with_travel.global.jwt.service;
//
//import com.arom.with_travel.domain.member.Member;
//import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
//import com.arom.with_travel.global.oauth2.dto.OAuth2Response;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.Base64;
//import java.util.Date;
//import java.util.Map;
//
//import static com.arom.with_travel.global.jwt.config.JwtProperties.*;
//
//@Getter
//@Component
//@Slf4j
//public class TokenProvider {
//
//    private final SecretKey SECRET_KEY;
//
//    public TokenProvider(
//            @Value("${jwt.secret-key}") String secretKey
//    ) {
//        byte[] keyBytes = Base64.getDecoder()
//                .decode(secretKey.getBytes(StandardCharsets.UTF_8));
//        this.SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
//    }
//
//    // 해당 유저의 정해진 기간으로 토큰 생성
//    public String generateToken(Member member, Duration expiredAt) {
//        Date now = new Date();
//        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
//    }
//
//    public String generateAccessToken(Member member) {
//        return Jwts.builder()
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME))
//                .setSubject(member.getEmail())
//                .claim("id",   member.getId())
//                .claim("role", member.getRole())
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//
//    public String generateRefreshToken(Member member) {
//        return Jwts.builder()
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME))
//                .setSubject(member.getEmail())
//                .claim("id",   member.getId())
//                .claim("role", member.getRole())
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//
//    // 토큰 생성 메서드
//    public String makeToken(Date expiry, Member member) {
//        Date now = new Date();
//        return Jwts.builder()
//                .setIssuedAt(now)
//                .setExpiration(expiry)
//                .setSubject(member.getEmail())
//                .claim("id",   member.getId())
//                .claim("role", member.getRole())
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//
//    // 토큰 유효성 검증 메서드
//    public boolean validToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                   ;
//
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public Authentication getAuthentication(String token) {
//        Claims c = getClaims(token);
//        Long   id   = c.get("id", Long.class);
//        String email = c.getSubject();
//        Member.Role role = Member.Role.valueOf(c.get("role", String.class));
//
//        // 임시 OAuth2Response (필요한 정보만)
//        OAuth2Response stub = new OAuth2Response() {
//            @Override public Map<String, Object> getAttribute() { return Map.of(); }
//            @Override public String getOauthId()    { return null; }
//            @Override public String getProvider()   { return null; }
//            @Override public String getEmail()      { return email; }
//            @Override public String getName()       { return null; }
//        };
//
//        CustomOAuth2User principal =
//                new CustomOAuth2User(stub, role, null);
//
//        return new UsernamePasswordAuthenticationToken(
//                principal, token, principal.getAuthorities());
//    }
//
////    // 토큰 인증정보 조회 메서드
////    public Authentication getAuthentication(String token) {
////        Claims claims = getClaims(token);
////        Set<SimpleGrantedAuthority> authoritySet = Collections.singleton(
////                new SimpleGrantedAuthority(claims.get("role").toString()));
////
////        return new UsernamePasswordAuthenticationToken(
////                new org.springframework.security.core.userdetails.User(
////                        claims.getSubject(),
////                        "",
////                        authoritySet
////                ), token, authoritySet);
////    }
//
//    // HttpServletRequest 에서 토큰을 파싱하여 로그인 이메일 반환
//    public String getMemberLoginEmail(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
//
//        String accessToken = null;
//        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            accessToken = authorizationHeader.substring(TOKEN_PREFIX.length());
//        }
//
//        if (accessToken != null) {
//            Claims claims = getClaims(accessToken);
//            return claims.getSubject();
//        }
//
//        return null;
//    }
//
//    // 토큰의 클레임 반환
//    public Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}
