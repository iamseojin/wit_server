package com.arom.with_travel.global.security.token.controller;

import com.arom.with_travel.global.security.token.dto.request.RefreshTokenRequest;
import com.arom.with_travel.global.security.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    @Operation(
            summary = "새로운 Access Token 생성",
            description = "Refresh Token을 사용하여 새로운 Access Token을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "새로운 Access Token 생성 성공",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
    })
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody @Valid RefreshTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(newAccessToken);
    }
}
