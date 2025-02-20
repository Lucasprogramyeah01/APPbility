package com.example.APPbility.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sexo {

    HOMBRE("Hombre"),
    MUJER("Mujer");

    private final String palabra;
}
