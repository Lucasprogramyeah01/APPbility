package com.example.APPbility.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Modalidad {

    PRESENCIAL("Presencial"),
    VIRTUAL("Virtual"),
    AMBAS("Ambas");

    private final String palabra;
}
