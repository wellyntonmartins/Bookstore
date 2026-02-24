package com.bookstore.dtos;

import jakarta.validation.constraints.NotNull;

public record ReviewRecordDto(
        @NotNull(message = "Comment can't be null") String comment
) {
}
