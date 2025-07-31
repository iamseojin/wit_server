package com.arom.with_travel.domain.accompanies.controller;

import com.arom.with_travel.domain.accompanies.dto.cursor.Cursor;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.dto.response.CursorSliceResponse;
import com.arom.with_travel.domain.accompanies.model.City;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.accompanies.service.AccompanySearchService;
import com.arom.with_travel.domain.accompanies.swagger.GetAccompaniesSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accompanies/search")
public class AccompanySearchController {

    private final AccompanySearchService accompanySearchService;

    @GetAccompaniesSearch
    @GetMapping
    public CursorSliceResponse<AccompanyBriefResponse> searchByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Continent continent,
            @RequestParam(required = false) Country country,
            @RequestParam(required = false) City city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        return accompanySearchService.getFilteredAccompanies(new Cursor(lastCreatedAt, lastId), keyword, continent, country, city, startDate, size);
    }
}
