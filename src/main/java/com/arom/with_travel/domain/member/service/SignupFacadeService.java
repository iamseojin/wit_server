package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.request.SignupWithSurveyRequestDto;
import com.arom.with_travel.domain.member.dto.response.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.domain.survey.service.SurveyService;
import com.arom.with_travel.global.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupFacadeService {

    private final MemberSignupService memberSignupService;
    private final SurveyService surveyService;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberSignupResponseDto fillExtraInfo(String email,
                                                      SignupWithSurveyRequestDto req) {
        MemberSignupResponseDto memberDto =
                memberSignupService.fillExtraInfo(email, req.getExtraInfo());

        req.getSurveys().forEach(dto -> surveyService.createSurvey(email, dto));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Member not found"));
        member.markAdditionalDataChecked();

        return memberDto;
    }
}