package com.bookstore.utils;

import com.bookstore.exceptions.DataFormatWrongException;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Methods {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static LocalDate parseDateToLocalDate(String date) {

        if (!StringUtils.hasText(date)) {
            throw new DataFormatWrongException("date cannot be null or empty");
        }

        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DataFormatWrongException("invalid date format. expected yyyy-MM-dd");
        }
    }
}
