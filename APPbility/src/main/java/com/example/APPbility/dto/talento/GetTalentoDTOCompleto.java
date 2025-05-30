package com.example.APPbility.dto.talento;

import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.model.Talento;
import com.example.APPbility.user.dto.GetUserDTOSinListas;

public record GetTalentoDTOCompleto(
        Long id,
        String titulo,
        String descripcion,
        String imagen,
        GetNivelDTO nivel,
        GetUserDTOSinListas usuario
) {

    public static GetTalentoDTOCompleto of(Talento t, GetNivelDTO nivel, GetUserDTOSinListas usuario){
        return new GetTalentoDTOCompleto(
                t.getId(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getImagen(),
                nivel,
                usuario
        );
    }

}
