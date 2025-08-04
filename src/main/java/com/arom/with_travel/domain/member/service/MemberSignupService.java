package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.request.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.response.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.dto.response.SocialMemberVerificationResponse;
import com.arom.with_travel.domain.member.dto.request.SocialMemberVerificationRequest;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import com.arom.with_travel.global.security.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberSignupService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public SocialMemberVerificationResponse verifyMember(SocialMemberVerificationRequest req){
        Member member = memberRepository.findByOauthId(req.getOauthId())
                .orElseGet(() -> {
                    Member newMember = Member.create(req.getName(), req.getEmail(), req.getOauthId());
                    return memberRepository.save(newMember);
                });
        boolean isChecked = member.getAdditionalDataChecked();
        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);
        log.info("[member id] : {}", member.getId());
        log.info("[access token] : {}", accessToken);
        log.info("[refresh token] : {}", refreshToken);
        return new SocialMemberVerificationResponse(isChecked, accessToken, refreshToken);
    }

    // 신규 회원 등록
    public Member createIfNotExists(CustomOAuth2User user) {
        return memberRepository
                .findByEmail(user.getEmail())
                .orElseGet(() -> {
                    Member inserted = Member.signUp(user.getEmail(), user.getOauthId());
                    return memberRepository.save(inserted);
                });
    }
  
    private Member getUserByLoginEmailOrElseThrow(String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 신규 회원 추가 정보 등록
    public MemberSignupResponseDto fillExtraInfo(String email,
                                                 MemberSignupRequestDto dto) {
        Member member = getUserByLoginEmailOrElseThrow(email);
        member.updateExtraInfo(dto.getNickname(),
                dto.getBirthdate(),
                dto.getGender());

        return MemberSignupResponseDto.from(member);
    }

    @Transactional(readOnly = true)
    public MemberSignupResponseDto getSignupInfo(String email) {
        Member member = getUserByLoginEmailOrElseThrow(email);
        return MemberSignupResponseDto.from(member);
    }
}
