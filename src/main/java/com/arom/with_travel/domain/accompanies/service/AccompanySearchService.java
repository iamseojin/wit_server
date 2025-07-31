package com.arom.with_travel.domain.accompanies.service;

import com.arom.with_travel.domain.accompanies.dto.cursor.Cursor;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.dto.response.CursorSliceResponse;
import com.arom.with_travel.domain.accompanies.model.*;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccompanySearchService {

    private final AccompanyRepository accompanyRepository;

    public CursorSliceResponse<AccompanyBriefResponse> getFilteredAccompanies(Cursor cursor,
                                                            String keyword,
                                                            Continent continent,
                                                            Country country,
                                                            City city,
                                                            LocalDate startDate,
                                                            int size) {
        Pageable pageable = PageRequest.of(0, Math.min(size, 100),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Slice<AccompanyBriefResponse> dtoSlice =
                accompanyRepository.findByFiltersWithNoOffset(
                        keyword, continent, country, city, startDate, cursor.lastCreatedAt(), cursor.lastId(), pageable
                        )
                        .map(AccompanyBriefResponse::from);
        return CursorSliceResponse.of(dtoSlice);
    }

}
