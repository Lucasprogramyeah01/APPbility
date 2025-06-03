package com.example.APPbility.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Estado {

    PROPUESTO("Propuesto"),
    RECHAZADO("Rechazado"),
    ACTIVO("Activo"),
    FINALIZADO("Finalizado");

    private final String palabra;
}
