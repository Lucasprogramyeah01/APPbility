package com.example.APPbility.dto.valoracion;

import com.example.APPbility.model.Valoracion;

import java.util.UUID;

public record GetValoracionDTOConAmbosUsuariosIDs(
        Long id,
        int puntuacion,
        String titulo,
        String resenha,
        UUID usuarioEscritorID,
        UUID usuarioValoradoID
) {

    public static GetValoracionDTOConAmbosUsuariosIDs of(Valoracion v, UUID usuarioEscritorID, UUID usuarioValoradoID){
        return new GetValoracionDTOConAmbosUsuariosIDs(
                v.getId(),
                v.getPuntuacion(),
                v.getTitulo(),
                v.getResenha(),
                usuarioEscritorID,
                usuarioValoradoID
        );
    }

}
