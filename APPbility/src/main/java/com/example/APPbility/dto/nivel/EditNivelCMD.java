package com.example.APPbility.dto.nivel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EditNivelCMD(
        @NotBlank(message = "{nivel.nombre.notBlank}")
        String nombre,

        @NotBlank(message = "{nivel.color.notBlank}")
        String color,

        @Min(value = 1, message = "{nivel.orden.min}")
        @NotNull(message = "{nivel.orden.notNull}")
        int orden
) {
}
