package com.example.APPbility.dto.bloque;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateBloqueCMD(
        @NotBlank(message = "{bloque.titulo.notBlank}")
        String titulo,

        @NotBlank(message = "{bloque.descripcion.notBlank}")
        String descripcion,

        @NotNull(message = "{bloque.hora.notNull}")
        LocalTime hora,

        @NotNull(message = "{sesion.fecha.notNull}")
        @FutureOrPresent(message = "{sesion.fecha.FutureOrPresent}")
        LocalDate fechaSesion,

        @NotNull(message = "{sesion.intercambioID.notNull}")
        Long intercambioID
) {
}
