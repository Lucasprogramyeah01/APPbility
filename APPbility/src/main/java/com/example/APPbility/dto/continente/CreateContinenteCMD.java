package com.example.APPbility.dto.continente;

import jakarta.validation.constraints.NotBlank;

import com.example.APPbility.validation.continente.UniqueNombreContinente;

public record CreateContinenteCMD(
        @NotBlank(message = "{continente.nombre.notBlank}")
        @UniqueNombreContinente
        String nombre
) {
}
