package com.bookstore.dtos;

import com.bookstore.enums.Shift;

public record StudentRecordDto(
        String name,
        Integer matriculation,
        Shift shift,
        String school_class
) {
}
