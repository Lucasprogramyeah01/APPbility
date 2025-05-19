package com.example.APPbility.error.custom;

import com.example.APPbility.error.CustomValidationException;

public class IncorrectSizeException extends CustomValidationException {
    public IncorrectSizeException(String message) {
        super(message);
    }
}
