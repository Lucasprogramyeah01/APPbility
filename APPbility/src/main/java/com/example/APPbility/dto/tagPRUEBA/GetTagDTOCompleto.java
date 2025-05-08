package com.example.APPbility.dto.tagPRUEBA;

import com.example.APPbility.model.TagPRUEBA;
import com.example.APPbility.user.dto.GetUserDTO;

import java.util.Set;

public record GetTagDTOCompleto(
        Long id,
        String nombre,
        Set<GetUserDTO> listaUsuarios
) {

    public static GetTagDTOCompleto of(TagPRUEBA t, Set<GetUserDTO> listaUsuarios){
        return new GetTagDTOCompleto(
             t.getId(),
             t.getNombre(),
             listaUsuarios
        );
    }

}
