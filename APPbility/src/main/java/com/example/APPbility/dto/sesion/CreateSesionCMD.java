package com.example.APPbility.dto.sesion;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateSesionCMD(
        @NotNull(message = "{sesion.fecha.notNull}")
        @FutureOrPresent(message = "{sesion.fecha.FutureOrPresent}")
        LocalDate fecha
) {
}
