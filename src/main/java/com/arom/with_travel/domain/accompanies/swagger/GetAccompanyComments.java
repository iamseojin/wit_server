package com.arom.with_travel.domain.accompanies.swagger;


import com.arom.with_travel.domain.accompanies.dto.response.AccompanyCommentSliceResponse;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(
        summary     = "동행 댓글 목록 조회",
        description = """
            커서(lastCreatedAt, lastId) 기반 무한 스크롤 Slice 방식으로
            특정 동행(accompanyId)의 댓글을 최신순으로 조회합니다.
            """
)
@Parameter(name = "accompanyId", in = ParameterIn.PATH, required = true,
        description = "동행 ID", example = "15")
@Parameter(name = "lastCreatedAt", in = ParameterIn.QUERY, required = false,
        description = "마지막으로 받은 댓글의 생성 시각(ISO-8601)", example = "2025-07-20T13:30:00")
@Parameter(name = "lastId", in = ParameterIn.QUERY, required = false,
        description = "마지막으로 받은 댓글 ID", example = "37")
@Parameter(name = "size", in = ParameterIn.QUERY, required = false,
        description = "한 번에 가져올 댓글 수", schema = @Schema(defaultValue = "10", minimum = "1", maximum = "50"))
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = AccompanyCommentSliceResponse.class))),
        @ApiResponse(responseCode = "404", description = "동행을 찾을 수 없음",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface GetAccompanyComments {}
