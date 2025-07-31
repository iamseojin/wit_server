package com.arom.with_travel.domain.accompanies.repository.accompany;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.City;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AccompanyRepository extends JpaRepository<Accompany, Long>{

    @Query("SELECT a FROM Accompany a WHERE a.continent = :continent")
    Page<Accompany> findByContinent(@Param("continent") Continent continent, Pageable pageable);

    @Query("SELECT a FROM Accompany a WHERE a.country = :country  AND :lastId IS NULL OR a.id < :lastId")
    Slice<Accompany> findByCountry(
            @Param("country") Country country,
            @Param("lastId") Long lastId,
            Pageable pageable);
    
    @Query("""
SELECT a
FROM   Accompany a
WHERE  (:continent  IS NULL OR a.continent  = :continent)
  AND  (:country    IS NULL OR a.country    = :country)
  AND  (:city       IS NULL OR a.city       = :city)
  AND  (:startDate  IS NULL OR a.startDate >= :startDate)

  AND  (
        :keyword IS NULL OR :keyword = '' OR
        LOWER(a.title)   LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
       )
  AND  (
        :lastCreatedAt IS NULL OR
        (a.createdAt < :lastCreatedAt) OR
        (a.createdAt = :lastCreatedAt AND a.id < :lastId)
       )
ORDER BY a.createdAt DESC, a.id DESC
""")
    Slice<Accompany> findByFiltersWithNoOffset(
            @Param("keyword")       String keyword,
            @Param("continent")     Continent continent,
            @Param("country")       Country country,
            @Param("city")          City city,
            @Param("startDate")     LocalDate startDate,
            @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
            @Param("lastId")        Long lastId,
            Pageable pageable
    );

    List<Accompany> findAccompaniesByMember(Member member);
}
