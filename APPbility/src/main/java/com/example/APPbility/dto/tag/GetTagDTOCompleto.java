package com.example.APPbility.dto.tag;

import com.example.APPbility.model.Tag;
import com.example.APPbility.user.dto.GetUserDTO;

import java.util.Set;

public record GetTagDTOCompleto(
        Long id,
        String nombre,
        Set<GetUserDTO> listaUsuarios
) {

    public static GetTagDTOCompleto of(Tag t, Set<GetUserDTO> listaUsuarios){
        return new GetTagDTOCompleto(
             t.getId(),
             t.getNombre(),
             listaUsuarios
        );
    }

}
