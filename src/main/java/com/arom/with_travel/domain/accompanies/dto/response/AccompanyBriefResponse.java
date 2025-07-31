package com.arom.with_travel.domain.accompanies.dto.response;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.Country;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class AccompanyBriefResponse implements CursorSliceResponse.HasCreatedAt,
        CursorSliceResponse.HasId{
    private Long id;
    private Country country;
    private LocalDate startDate;
    private LocalTime startTime;
    private String title;
    private int maxParticipants;
    private String writer;
    private Long views;
    private LocalDateTime createdAt;

    public static AccompanyBriefResponse from(Accompany accompany){
        return AccompanyBriefResponse.builder()
                .id(accompany.getId())
                .country(accompany.getCountry())
                .startDate(accompany.getStartDate())
                .startTime(accompany.getStartTime())
                .title(accompany.getTitle())
                .maxParticipants(accompany.getMaxParticipants())
                .writer(accompany.getMember().getNickname())
                .views(accompany.getViews())
                .createdAt(accompany.getCreatedAt())
                .build();
    }

    @Override public LocalDateTime createdAt() { return createdAt; }
    @Override public Long id()       { return id; }
}
