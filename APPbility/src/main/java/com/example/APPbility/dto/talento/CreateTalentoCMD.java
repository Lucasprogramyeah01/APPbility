package com.example.APPbility.dto.talento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTalentoCMD(
        @NotBlank(message = "{talento.titulo.notBlank}")
        String titulo,

        @NotBlank(message = "{talento.descripcion.notBlank}")
        String descripcion,

        @NotNull(message = "{talento.nivelID.notNull}")
        Long nivelID
) {
}
