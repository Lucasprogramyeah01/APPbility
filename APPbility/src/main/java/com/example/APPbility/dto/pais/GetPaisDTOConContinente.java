package com.example.APPbility.dto.pais;

import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.model.Pais;

public record GetPaisDTOConContinente(
        Long id,
        String nombre,
        String codigoISO,
        String bandera,
        GetContinenteDTO continente
) {

    public static GetPaisDTOConContinente of (Pais p, GetContinenteDTO continente){
        return new GetPaisDTOConContinente(
                p.getId(),
                p.getNombre(),
                p.getCodigoISO(),
                p.getBandera(),
                continente
        );
    }

}
