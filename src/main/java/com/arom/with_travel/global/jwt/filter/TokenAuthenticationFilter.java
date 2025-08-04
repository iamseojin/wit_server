//package com.arom.with_travel.global.jwt.filter;
//
//import com.arom.with_travel.global.jwt.service.TokenProvider;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//import static com.arom.with_travel.global.jwt.config.JwtProperties.HEADER_AUTHORIZATION;
//import static com.arom.with_travel.global.jwt.config.JwtProperties.TOKEN_PREFIX;
//
//@Slf4j
//@RequiredArgsConstructor
//public class TokenAuthenticationFilter extends OncePerRequestFilter {
//
//    private final TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//        String jwtHeader = request.getHeader(HEADER_AUTHORIZATION);
//        if (jwtHeader == null || !jwtHeader.startsWith(TOKEN_PREFIX)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
//        String accessToken = getAccessToken(authorizationHeader);
//
//        // 엑세스 토큰이 있는 경우, SrcurityContextHolder에 유저 정보 등록.
//        if (accessToken != null) {
//            Authentication authentication = tokenProvider.getAuthentication(accessToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    // 해당 헤더에서 엑세스 토큰 조회
//    private String getAccessToken(String authorizationHeader) {
//        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            return authorizationHeader.substring(TOKEN_PREFIX.length());
//        }
//        return null;
//    }
//}
