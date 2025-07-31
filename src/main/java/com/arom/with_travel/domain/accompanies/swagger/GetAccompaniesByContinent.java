package com.arom.with_travel.domain.accompanies.swagger;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
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
        summary     = "동행 검색 (대륙별, 페이지네이션)",
        description = """
            지정한 대륙(continent)의 동행을 페이지네이션(Pageable)으로 조회합니다.
            기본 정렬: createdAt DESC, size=10
            """
)
@Parameter(name = "continent", in = ParameterIn.QUERY, required = false,
        description = "대륙 코드", example = "ASIA")
@Parameter(name = "page", in = ParameterIn.QUERY, required = false,
        description = "0-base 페이지 번호", example = "0")
@Parameter(name = "size", in = ParameterIn.QUERY, required = false,
        description = "페이지 크기", example = "10")
@Parameter(name = "sort", in = ParameterIn.QUERY, required = false,
        description = "정렬 조건", example = "createdAt,desc")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = AccompanyBriefResponse.class)))
})
public @interface GetAccompaniesByContinent {}