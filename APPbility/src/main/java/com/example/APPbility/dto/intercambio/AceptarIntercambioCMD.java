package com.example.APPbility.dto.intercambio;

import jakarta.validation.constraints.NotNull;

public record AceptarIntercambioCMD(
        @NotNull(message = "{intercambio.talentoAceptadoID.notNull}")
        Long talentoAceptadoID
) {
}
