package com.arom.with_travel.global.security.token.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import com.arom.with_travel.global.security.token.domain.RefreshToken;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import com.arom.with_travel.global.security.token.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TokenService {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;

    // 새로운 액세스 토큰 생성
    public String createNewAccessToken(String refreshToken) {
        validateRefreshTokenOrElseThrow(refreshToken);
        Long userId = loadRefreshTokenOrThrow(refreshToken).getMemberId();
        Member member = memberService.getUserByUserIdOrElseThrow(userId);
        return jwtProvider.generateAccessToken(member);
    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(String loginEmail) {
        Member member = memberService.getUserByLoginEmailOrElseThrow(loginEmail);
        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(member.getId())
                .orElseThrow(() -> BaseException.from(ErrorCode.TOKEN_NOT_FOUND));

        refreshTokenRepository.delete(refreshToken);
    }

    private void validateRefreshTokenOrElseThrow(String refreshToken) {
        if (!jwtProvider.isRefreshTokenExpired(refreshToken)) {
            throw BaseException.from(ErrorCode.INVALID_TOKEN);
        }
    }

    public AuthTokenResponse issueTokenPair(String loginEmail) {
        Member member = memberService.getUserByLoginEmailOrElseThrow(loginEmail);
        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);
        return new AuthTokenResponse(accessToken, refreshToken);
    }

    private RefreshToken loadRefreshTokenOrThrow(String refreshToken) {
        return refreshTokenRepository.findByJwtValue(refreshToken)
                .orElseThrow(() -> BaseException.from(ErrorCode.TOKEN_NOT_FOUND));
    }
}
