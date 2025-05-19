package com.example.APPbility.dto.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditPaisCMD(
        Long id,

        @NotBlank(message = "{pais.nombre.notBlank}")
        String nombre,

        @NotBlank(message = "{pais.codigoISO.notBlank}")
        String codigoISO,

        @NotNull(message = "{pais.continenteID.notNull}")
        Long continenteID
) {
}
