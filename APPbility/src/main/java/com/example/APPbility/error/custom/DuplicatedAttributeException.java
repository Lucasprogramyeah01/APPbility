package com.example.APPbility.error.custom;

import com.example.APPbility.error.CustomValidationException;

public class DuplicatedAttributeException extends CustomValidationException {
    public DuplicatedAttributeException(String message) {
        super(message);
    }
}
