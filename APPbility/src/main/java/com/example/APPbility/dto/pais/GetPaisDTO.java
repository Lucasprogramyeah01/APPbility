package com.example.APPbility.dto.pais;

import com.example.APPbility.model.Pais;

public record GetPaisDTO(
        Long id,
        String nombre,
        String codigoISO,
        String bandera
) {

    public static GetPaisDTO of (Pais p){
        return new GetPaisDTO(
                p.getId(),
                p.getNombre(),
                p.getCodigoISO(),
                p.getBandera()
        );
    }

}
