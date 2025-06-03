package com.example.APPbility.dto.tagPRUEBA;

import com.example.APPbility.model.TagPRUEBA;
import com.example.APPbility.user.dto.GetUserDTOConPaises;

import java.util.Set;

public record GetTagDTOCompleto(
        Long id,
        String nombre,
        Set<GetUserDTOConPaises> listaUsuarios
) {

    public static GetTagDTOCompleto of(TagPRUEBA t, Set<GetUserDTOConPaises> listaUsuarios){
        return new GetTagDTOCompleto(
             t.getId(),
             t.getNombre(),
             listaUsuarios
        );
    }

}
