package com.example.APPbility.dto.valoracion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateValoracionCMD(
        @Min(value = 1, message = "{validacion.puntuacion.min}")
        @Max(value = 10, message = "{validacion.puntuacion.max}")
        @NotNull(message = "{validacion.puntuacion.notNull}")
        int puntuacion,

        String titulo,

        @NotBlank(message = "{validacion.resenha.notBlank}")
        String resenha
) {
}
