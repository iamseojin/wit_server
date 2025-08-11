package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.dto.request.SignupWithSurveyRequestDto;
import com.arom.with_travel.domain.member.dto.request.SocialMemberVerificationRequest;
import com.arom.with_travel.domain.member.dto.response.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.dto.response.MemberSignupTokenResponse;
import com.arom.with_travel.domain.member.dto.response.SocialMemberVerificationResponse;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import com.arom.with_travel.global.security.token.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/signup")
@Tag(name = "회원가입", description = "카카오 신규 회원 추가 정보 입력 API")
@Slf4j
public class MemberSignupController {

    private final MemberSignupService memberSignupService;
    private final TokenService tokenService;

    // 추가 정보 및 설문 조사 등록
    @PostMapping("/register")
    public ResponseEntity<MemberSignupResponseDto> fillExtraInfo(
            @AuthenticationPrincipal PrincipalDetails principal,
            @RequestBody @Valid SignupWithSurveyRequestDto req) {

        AuthenticatedMember member = principal.getAuthenticatedMember();

        MemberSignupResponseDto dto =
                memberSignupService.registerWithSurvey(member.getEmail(), req);

        return ResponseEntity.ok(dto);
    }

    // 현재 로그인 사용자 정보 조회
    @GetMapping(value = "/register")
    public ResponseEntity<MemberSignupTokenResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principal) {
        AuthenticatedMember member = principal.getAuthenticatedMember();
        MemberSignupTokenResponse signupDto = memberSignupService.getSignupInfo(member.getEmail());
        return ResponseEntity.ok(signupDto);
    }

    @PostMapping("/member/verify")
    public SocialMemberVerificationResponse checkMember(@RequestBody @Valid SocialMemberVerificationRequest req){
        return memberSignupService.verifyMember(req);
    }

    @GetMapping("/member/nickname/duplicate")
    public boolean checkNicknameDuplicate(@RequestParam String nickname) {
        return memberSignupService.isNicknameDuplicated(nickname);
    }
}
