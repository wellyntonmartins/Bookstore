package com.bookstore.dtos;

import com.bookstore.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record LoanRecordDto(
    @NotNull(message = "Book Id can't be null") UUID bookId,
    @NotNull(message = "Student Id can't be null") UUID studentId,
    @NotNull(message = "Date can't be null") String date,
    @NotNull(message = "Due date can't be null") String due_date,
    @NotNull(message = "Status can't be null") LoanStatus status
) {
}
