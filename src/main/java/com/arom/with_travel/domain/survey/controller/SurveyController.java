// SurveyController.java
package com.arom.with_travel.domain.survey.controller;

import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import com.arom.with_travel.domain.survey.dto.response.SurveyResponseDto;
import com.arom.with_travel.domain.survey.service.SurveyService;
import com.arom.with_travel.domain.survey.swagger.GetMySurveys;
import com.arom.with_travel.domain.survey.swagger.GetSingleSurvey;
import com.arom.with_travel.domain.survey.swagger.PostNewSurvey;
import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SurveyController {

    private final SurveyService surveyService;

    @PostNewSurvey
    @PostMapping("/surveys")
    public void createSurvey(@AuthenticationPrincipal PrincipalDetails principal,
                             @RequestBody SurveyRequestDto dto) {
        AuthenticatedMember member = principal.getAuthenticatedMember();
        surveyService.createSurvey(member.getEmail(), dto);
    }

    @GetSingleSurvey
    @GetMapping("/survey/{surveyId}")
    public SurveyResponseDto getSurvey(@PathVariable Long surveyId) {
        return surveyService.getSurvey(surveyId);
    }

    @GetMySurveys
    @GetMapping("/surveys/my")
    public List<SurveyResponseDto> getMySurveys(@AuthenticationPrincipal PrincipalDetails principal) {
        AuthenticatedMember member = principal.getAuthenticatedMember();
        return surveyService.getSurveysByEmail(member.getEmail());
    }
}
