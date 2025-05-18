package com.example.APPbility.dto.pais;

import com.example.APPbility.validation.pais.create.UniqueCodigoISOPaisCreate;
import com.example.APPbility.validation.pais.create.UniqueNombrePaisCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePaisCMD(
        @NotBlank(message = "{pais.nombre.notBlank}")
        @UniqueNombrePaisCreate
        String nombre,

        @Size(min = 2, max = 2, message = "{pais.codigoISO.size}")
        @NotBlank(message = "{pais.codigoISO.notBlank}")
        @UniqueCodigoISOPaisCreate
        String codigoISO,

        @NotNull(message = "{pais.continenteID.notNull}")
        Long continenteID
) {
}
