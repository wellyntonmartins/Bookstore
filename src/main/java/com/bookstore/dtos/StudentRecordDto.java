package com.bookstore.dtos;

import com.bookstore.enums.Shift;
import jakarta.validation.constraints.NotNull;

public record StudentRecordDto(
        @NotNull(message = "Name can't be null") String name,
        @NotNull(message = "Matriculation can't be null") Integer matriculation,
        @NotNull(message = "Shift can't be null") Shift shift,
        @NotNull(message = "School class can't be null") String school_class
) {
}
