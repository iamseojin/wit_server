package com.arom.with_travel.domain.accompanies.swagger;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
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
        summary     = "동행 검색 (키워드·지역·날짜)",
        description = """
            키워드 또는 대륙·국가·도시·출발일 필터로 동행을 검색합니다.
            커서(lastId) 기반 Slice 페이징을 사용합니다.
            """
)
@Parameter(name = "keyword",     in = ParameterIn.QUERY, required = false,
        description = "제목/닉네임 검색 키워드", example = "유럽 배낭여행")
@Parameter(name = "continent",   in = ParameterIn.QUERY, required = false,
        description = "대륙 코드", example = "EUROPE")
@Parameter(name = "country",     in = ParameterIn.QUERY, required = false,
        description = "국가 코드", example = "FRANCE")
@Parameter(name = "city",        in = ParameterIn.QUERY, required = false,
        description = "도시 코드", example = "PARIS")
@Parameter(name = "startDate",   in = ParameterIn.QUERY, required = false,
        description = "여행 시작일(YYYY-MM-DD)", example = "2025-08-01")
@Parameter(name = "lastId",      in = ParameterIn.QUERY, required = false,
        description = "마지막으로 받은 동행 ID(커서)", example = "42")
@Parameter(name = "size",        in = ParameterIn.QUERY, required = false,
        description = "페이지 크기", schema = @Schema(defaultValue = "10", minimum = "1", maximum = "50"))
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = AccompanyBriefResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 파라미터",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface GetAccompaniesSearch {}