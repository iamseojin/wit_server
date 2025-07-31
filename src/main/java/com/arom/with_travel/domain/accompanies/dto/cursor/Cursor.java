package com.arom.with_travel.domain.accompanies.dto.cursor;


import java.time.LocalDateTime;

public record Cursor(LocalDateTime lastCreatedAt, Long lastId) {}
