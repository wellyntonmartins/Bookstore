package com.bookstore.dtos;

import com.bookstore.enums.UserAccessType;
import jakarta.validation.constraints.NotNull;

public record UserRecordDto(
        @NotNull(message = "Name can't be null") String name,
        @NotNull(message = "Email can't be null") String email,
        @NotNull(message = "Password can't be null") String password,
        @NotNull(message = "Access type can't be null") UserAccessType access_type
) {
}
