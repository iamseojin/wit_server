package com.arom.with_travel.global.security.token.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    @NotEmpty private String refreshToken;
}
