package com.arom.with_travel.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocialMemberVerificationResponse {
    private boolean isInfoChecked;
    private String accessToken;
    private String refreshToken;
}
