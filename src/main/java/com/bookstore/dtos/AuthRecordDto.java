package com.bookstore.dtos;

import jakarta.validation.constraints.NotNull;

public record AuthRecordDto(
        @NotNull(message = "Email can't be null.") String email,
        @NotNull(message = "Password can't be null.") String password) {
}
