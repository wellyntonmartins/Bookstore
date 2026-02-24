package com.bookstore.dtos;


import jakarta.validation.constraints.NotNull;

public record AuthorRecordDto(
        @NotNull(message = "Name can't be null") String name
) {
}
