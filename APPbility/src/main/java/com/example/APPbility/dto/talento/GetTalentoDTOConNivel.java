package com.example.APPbility.dto.talento;

import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.model.Talento;

public record GetTalentoDTOConNivel(
        Long id,
        String titulo,
        String descripcion,
        String imagen,
        GetNivelDTO nivel
) {

    public static GetTalentoDTOConNivel of(Talento t, GetNivelDTO nivel){
        return new GetTalentoDTOConNivel(
                t.getId(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getImagen(),
                nivel
        );
    }

}
