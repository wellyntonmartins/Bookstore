package com.bookstore.exceptions;

public class DataFormatWrongException extends RuntimeException {
    public DataFormatWrongException(String message) {
        super(message);
    }
}
