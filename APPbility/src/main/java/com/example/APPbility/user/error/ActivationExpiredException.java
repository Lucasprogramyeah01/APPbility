package com.example.APPbility.user.error;

public class ActivationExpiredException extends RuntimeException {
    public ActivationExpiredException(String message) {
        super(message);
    }
}
