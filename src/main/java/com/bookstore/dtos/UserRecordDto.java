package com.bookstore.dtos;

import jakarta.validation.constraints.NotNull;

public record UserRecordDto(
        @NotNull(message = "Name can't be null") String name,
        @NotNull(message = "Email can't be null") String email,
        @NotNull(message = "Password can't be null") String password
) {
}
