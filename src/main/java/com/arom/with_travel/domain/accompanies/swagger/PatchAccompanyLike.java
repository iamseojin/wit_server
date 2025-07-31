package com.arom.with_travel.domain.accompanies.swagger;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "동행 좋아요", description = "특정 ID에 해당하는 동행에 좋아요를 토글합니다.")
@Parameter(
        name        = "accompanyId",
        description = "좋아요 토글할 동행 ID",
        in          = ParameterIn.PATH,
        required    = true,
        example     = "3"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "좋아요 토글 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = AccompanyDetailsResponse.class))),
        @ApiResponse(responseCode = "404", description = "회원 또는 동행을 찾을 수 없음",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface PatchAccompanyLike {}