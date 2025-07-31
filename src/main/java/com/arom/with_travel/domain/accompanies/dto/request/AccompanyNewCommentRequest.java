package com.arom.with_travel.domain.accompanies.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AccompanyNewCommentRequest {
    @NotEmpty private String comment;
}
