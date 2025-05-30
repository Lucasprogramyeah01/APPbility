package com.example.APPbility.error.custom;

public class UnauthorizedAccessException extends CustomValidationException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
