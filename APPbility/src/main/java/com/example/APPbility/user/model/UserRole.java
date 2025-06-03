package com.example.APPbility.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN");

    private final String palabra;
}
