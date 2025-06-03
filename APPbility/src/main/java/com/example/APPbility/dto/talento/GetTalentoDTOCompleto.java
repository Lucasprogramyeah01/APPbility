package com.example.APPbility.dto.talento;

import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.model.Talento;
import com.example.APPbility.user.dto.GetUserDTO;

public record GetTalentoDTOCompleto(
        Long id,
        String titulo,
        String descripcion,
        String imagen,
        GetNivelDTO nivel,
        GetUserDTO usuario
) {

    public static GetTalentoDTOCompleto of(Talento t, GetNivelDTO nivel, GetUserDTO usuario){
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
