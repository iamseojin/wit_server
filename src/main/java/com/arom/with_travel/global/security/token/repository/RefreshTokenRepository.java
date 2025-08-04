package com.arom.with_travel.global.security.token.repository;

import com.arom.with_travel.global.security.token.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByJwtValue(String token);
    Optional<RefreshToken> findByMemberId(Long memberId);
}
