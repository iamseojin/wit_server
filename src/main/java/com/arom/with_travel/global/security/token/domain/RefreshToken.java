package com.arom.with_travel.global.security.token.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false, updatable = false, unique = true)
    private Long memberId;

    @Column(name = "value", nullable = false)
    private String jwtValue;

    private RefreshToken(Long memberId, String jwtValue) {
        this.memberId = memberId;
        this.jwtValue = jwtValue;
    }

    public static RefreshToken create(Long memberId, String jwtValue) {
        return new RefreshToken(memberId, jwtValue);
    }

    public RefreshToken update(String jwtValue) {
        this.jwtValue = jwtValue;
        return this;
    }
}
