package com.bookstore.dtos;

public record AuthRecordResponseDto(
        String accessToken,
        Long expiresIn) {
}
