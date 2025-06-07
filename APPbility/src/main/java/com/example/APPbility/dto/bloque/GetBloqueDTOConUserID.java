package com.example.APPbility.dto.bloque;

import com.example.APPbility.model.Bloque;
import com.example.APPbility.user.model.User;

import java.time.LocalTime;
import java.util.UUID;

public record GetBloqueDTOConUserID(
        Long id,
        String titulo,
        String descripcion,
        LocalTime hora,
        UUID usuarioID,
        String username
) {

    public static GetBloqueDTOConUserID of(Bloque b, User usuario){
        return new GetBloqueDTOConUserID(
                b.getId(),
                b.getTitulo(),
                b.getDescripcion(),
                b.getHora(),
                usuario.getId(),
                usuario.getUsername()
        );
    }

}
