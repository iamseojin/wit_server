package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.domain.image.dto.ImageRequest;
import com.arom.with_travel.domain.member.dto.response.MemberProfileResponseDto;
import com.arom.with_travel.domain.member.service.MemberProfileService;
import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @GetMapping("/myPostAccompany")
    public List<AccompanyDetailsResponse> myPost(@AuthenticationPrincipal PrincipalDetails principal){
        AuthenticatedMember member = principal.getAuthenticatedMember();
        return memberProfileService.myPostAccompany(member.getEmail());
    }

    @GetMapping("/myApplyAccompany")
    public List<AccompanyDetailsResponse> myApply(@AuthenticationPrincipal PrincipalDetails principal){
        AuthenticatedMember member = principal.getAuthenticatedMember();
        return memberProfileService.myApplyAccompany(member.getEmail());
    }

    @GetMapping("/myPastAccompany")
    public List<AccompanyDetailsResponse> myPast(@AuthenticationPrincipal PrincipalDetails principal){
        AuthenticatedMember member = principal.getAuthenticatedMember();
        return memberProfileService.myPastAccompany(member.getEmail());
    }

    @GetMapping("/main")
    public MemberProfileResponseDto getProfile(@AuthenticationPrincipal PrincipalDetails principal){
        AuthenticatedMember member = principal.getAuthenticatedMember();
        return memberProfileService.getProfile(member.getEmail());
    }

    @GetMapping("/introduction")
    public String getIntroduction(@AuthenticationPrincipal PrincipalDetails principal){
        AuthenticatedMember member = principal.getAuthenticatedMember();
        return memberProfileService.getIntroduction(member.getEmail());
    }

    @PostMapping("/profile-image")
    public void uploadProfileImage(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody ImageRequest request){
        AuthenticatedMember member = principal.getAuthenticatedMember();
        memberProfileService.uploadMemberProfileImage(member.getEmail(), request.getImageName(), request.getImageUrl());
    }
    //note 동행후기, 작성한글, 좋아요 누른 글 => 추가하기
}
