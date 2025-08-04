package com.arom.with_travel.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SocialMemberVerificationRequest {
    @NotBlank private String oauthId;
    @NotBlank private String email;
    @NotBlank private String name;
}
