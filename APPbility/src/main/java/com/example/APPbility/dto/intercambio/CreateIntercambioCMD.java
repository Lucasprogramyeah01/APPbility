package com.example.APPbility.dto.intercambio;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateIntercambioCMD(
        @NotNull(message = "{intercambio.usuarioSolicitadoID.notNull}")
        UUID usuarioSolicitadoID,

        @NotNull(message = "{intercambio.talentoSolicitadoID.notNull}")
        Long talentoSolicitadoID,

        @NotNull(message = "{intercambio.talentoSugeridoID.notNull}")
        Long talentoSugeridoID
) {
}
