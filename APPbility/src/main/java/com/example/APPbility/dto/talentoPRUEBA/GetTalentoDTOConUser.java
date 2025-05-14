package com.example.APPbility.dto.talentoPRUEBA;

import com.example.APPbility.model.TalentoPRUEBA;
import com.example.APPbility.user.dto.GetUserDTO;

import java.util.List;

public record GetTalentoDTOConUser(
        Long id,
        String titulo,
        String descripcion,
        List<String> listaImagenes,
        GetUserDTO usuario
) {

    public static GetTalentoDTOConUser of(TalentoPRUEBA t, GetUserDTO usuario){
        return new GetTalentoDTOConUser(
                t.getId(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getListaImagenes(),
                usuario
        );
    }

}
