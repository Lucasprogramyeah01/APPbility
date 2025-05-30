package com.example.APPbility.dto.continente;

import jakarta.validation.constraints.NotBlank;

public record EditContinenteCMD(
        @NotBlank(message = "{continente.nombre.notBlank}")
        String nombre
) {
}
