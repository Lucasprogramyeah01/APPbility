package com.example.APPbility.dto.bloque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record EditBloqueCMD(
        @NotBlank(message = "{bloque.titulo.notBlank}")
        String titulo,

        @NotBlank(message = "{bloque.descripcion.notBlank}")
        String descripcion,

        @NotNull(message = "{bloque.hora.notNull}")
        LocalTime hora
) {
}
