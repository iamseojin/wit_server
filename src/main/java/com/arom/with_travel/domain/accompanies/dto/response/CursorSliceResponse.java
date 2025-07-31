package com.arom.with_travel.domain.accompanies.dto.response;

import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;


public record CursorSliceResponse<T>(
        List<T> content,
        boolean hasNext,
        LocalDateTime nextCreatedAt,
        Long nextId) {

    public static <T extends HasCreatedAt & HasId>
    CursorSliceResponse<T> of(Slice<T> slice) {
        boolean hasNext = slice.hasNext();
        LocalDateTime nextCreatedAt = null;
        Long nextId = null;
        if (hasNext && !slice.isEmpty()) {
            List<T> list = slice.getContent();
            T last = list.get(list.size() - 1);
            nextCreatedAt = last.createdAt();
            nextId        = last.id();
        }
        return new CursorSliceResponse<>(slice.getContent(),
                hasNext,
                nextCreatedAt,
                nextId);
    }

    public interface HasCreatedAt { LocalDateTime createdAt(); }
    public interface HasId        { Long id(); }
}