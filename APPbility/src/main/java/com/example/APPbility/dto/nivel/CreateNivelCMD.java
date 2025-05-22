package com.example.APPbility.dto.nivel;

import com.example.APPbility.validation.nivel.UniqueColorNivel;
import com.example.APPbility.validation.nivel.UniqueNombreNivel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateNivelCMD(
        @NotBlank(message = "{nivel.nombre.notBlank}")
        @UniqueNombreNivel
        String nombre,

        @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "{nivel.color.pattern}")
        @NotBlank(message = "{nivel.color.notBlank}")
        @UniqueColorNivel
        String color,

        @Min(value = 1, message = "{nivel.orden.min}")
        @NotNull(message = "{nivel.orden.notNull}")
        int orden
) {
}
