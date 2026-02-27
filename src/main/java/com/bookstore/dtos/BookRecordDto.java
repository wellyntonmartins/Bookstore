package com.bookstore.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record BookRecordDto(
        @NotNull(message = "Title can't be null") String title,
        @NotNull(message = "Publisher can't be null") UUID publisherId,
        @NotNull(message = "Authors Ids can't be null") Set<UUID> authorsIds,
        String reviewComment,
        @NotNull(message = "Available quantity can't be null") int available_quantity,
        @NotNull(message = "ISBN can't be null") String isbn

) {

}
