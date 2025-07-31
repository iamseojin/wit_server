package com.arom.with_travel.domain.accompanies.swagger;

import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(summary = "동행 신청", description = "로그인 사용자가 특정 동행(accompanyId)에 신청(또는 신청 취소)합니다.")
@Parameter(
        name        = "accompanyId",
        description = "신청할 동행 ID",
        in          = ParameterIn.PATH,
        required    = true,
        example     = "3"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description  = "동행 신청(또는 취소) 성공",
                content      = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                        schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
                responseCode = "404",
                description  = "회원 또는 동행을 찾을 수 없음",
                content      = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
                responseCode = "401",
                description  = "로그인 필요",
                content      = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class))
        )
})
public @interface PostAccompanyApply {}