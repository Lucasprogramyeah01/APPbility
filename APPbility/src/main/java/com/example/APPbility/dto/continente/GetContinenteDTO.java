package com.example.APPbility.dto.continente;

import com.example.APPbility.model.Continente;

public record GetContinenteDTO(
        Long id,
        String nombre
) {

    public static GetContinenteDTO of(Continente c){
        return new GetContinenteDTO(
                c.getId(),
                c.getNombre()
        );
    }

}
