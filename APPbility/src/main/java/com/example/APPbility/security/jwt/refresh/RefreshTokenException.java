package com.example.APPbility.security.jwt.refresh;

import com.example.APPbility.security.exceptionHandling.JwtException;

public class RefreshTokenException extends JwtException {
    public RefreshTokenException(String message) {
        super(message);
    }
}
