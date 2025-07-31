package com.arom.with_travel.domain.accompany.service;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.City;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.accompanies.service.AccompanySearchService;
import com.arom.with_travel.domain.accompany.AccompanyFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;


@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
public class AccompanySearchServiceTest {

    @Mock
    private AccompanyRepository accompanyRepository;

    @InjectMocks
    private AccompanySearchService accompanySearchService;

    private Accompany accompany;

    @BeforeEach
    void setUp() {
        accompany = AccompanyFixture.동행_생성();
        accompany.post(AccompanyFixture.동행_작성자_생성());
        setField(accompany, "id", 1L);
    }
}
