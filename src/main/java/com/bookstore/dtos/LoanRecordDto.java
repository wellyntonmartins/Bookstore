package com.bookstore.dtos;

import com.bookstore.enums.LoanStatus;

import java.util.Date;
import java.util.UUID;

public record LoanRecordDto(
    UUID bookId,
    UUID studentId,
    Date date,
    Date due_date,
    LoanStatus status
) {
}
