package com.bookstore.dtos;

import java.util.Set;
import java.util.UUID;

public record BookRecordDto(
        String title,
        UUID publisherId,
        Set<UUID> authorsIds,
        String reviewComment,
        int available_quantity
) {

}
