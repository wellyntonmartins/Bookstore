package com.bookstore.dtos;

import com.bookstore.enums.UserAccessType;

public record UserRecordDto(
        String name,
        String email,
        String password,
        UserAccessType access_type
) {
}
