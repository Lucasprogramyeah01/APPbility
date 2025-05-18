package com.example.APPbility.dto.pais;

import com.example.APPbility.validation.pais.edit.UniqueCodigoISOPaisEdit;
import com.example.APPbility.validation.pais.edit.UniqueNombrePaisEdit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@UniqueNombrePaisEdit
@UniqueCodigoISOPaisEdit
public record EditPaisCMD(
        Long id,

        @NotBlank(message = "{pais.nombre.notBlank}")
        String nombre,

        @Size(min = 2, max = 2, message = "{pais.codigoISO.size}")
        @NotBlank(message = "{pais.codigoISO.notBlank}")
        String codigoISO,

        @NotNull(message = "{pais.continenteID.notNull}")
        Long continenteID
) {
}
