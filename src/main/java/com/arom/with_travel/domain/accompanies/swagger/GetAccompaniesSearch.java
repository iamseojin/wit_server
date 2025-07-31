package com.arom.with_travel.domain.accompanies.swagger;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.dto.response.CursorSliceResponse;
import com.arom.with_travel.domain.accompanies.model.City;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
        summary     = "동행 검색",
        description = """
            키워드와 대륙/국가/도시/시작일 필터를 조합해 동행 목록을 커서 기반으로 검색합니다.
            무한 스크롤을 위해 `lastCreatedAt`·`lastId`를 커서 값으로 넘기세요.
            """
)
@Parameters({
        @Parameter(name = "keyword", in = ParameterIn.QUERY, description = "검색 키워드 (제목/설명)", required = false,
                example = "유럽 배낭여행"),
        @Parameter(name = "continent", in = ParameterIn.QUERY, description = "대륙", required = false,
                schema = @Schema(implementation = Continent.class)),
        @Parameter(name = "country", in = ParameterIn.QUERY, description = "국가", required = false,
                schema = @Schema(implementation = Country.class)),
        @Parameter(name = "city", in = ParameterIn.QUERY, description = "도시", required = false,
                schema = @Schema(implementation = City.class)),
        @Parameter(name = "startDate", in = ParameterIn.QUERY, description = "여행 시작일(yyyy-MM-dd)", required = false,
                schema = @Schema(type = "string", format = "date")),
        @Parameter(name = "lastCreatedAt",  in = ParameterIn.QUERY, description = "커서: 마지막 항목 생성시각(yyyy-MM-dd'T'HH:mm:ss)",
                required = false,
                schema = @Schema(type = "string", format = "date-time")),
        @Parameter(name = "lastId", in = ParameterIn.QUERY, description = "커서: 마지막 항목 ID", required = false,
                example = "123"),
        @Parameter(name = "size", in = ParameterIn.QUERY, description = "페이지 크기 (기본 10, 최대 50)", required = false,
                example = "10")
})
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "검색 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = CursorSliceResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface GetAccompaniesSearch {}