package com.example.APPbility.dto.continente;

import com.example.APPbility.validation.continente.edit.UniqueNombreContinenteEdit;
import jakarta.validation.constraints.NotBlank;

//@UniqueNombreContinenteEdit
public record EditContinenteCMD(
        Long id,

        @NotBlank(message = "{continente.nombre.notBlank}")
        String nombre
) {
}
