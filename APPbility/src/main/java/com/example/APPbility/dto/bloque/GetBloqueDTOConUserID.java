package com.example.APPbility.dto.bloque;

import com.example.APPbility.model.Bloque;

import java.time.LocalTime;
import java.util.UUID;

public record GetBloqueDTOConUserID(
        Long id,
        String titulo,
        String descripcion,
        LocalTime hora,
        UUID usuarioID
) {

    public static GetBloqueDTOConUserID of(Bloque b, UUID usuarioID){
        return new GetBloqueDTOConUserID(
                b.getId(),
                b.getTitulo(),
                b.getDescripcion(),
                b.getHora(),
                usuarioID
        );
    }

}
