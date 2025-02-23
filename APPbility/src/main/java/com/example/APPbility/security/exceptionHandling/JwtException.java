package com.example.APPbility.security.exceptionHandling;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
