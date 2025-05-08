package com.example.APPbility.dto.talentoPRUEBA;

import com.example.APPbility.model.TalentoPRUEBA;

import java.util.List;

public record GetTalentoDTO(
        Long id,
        String titulo,
        String descripcion,
        List<String> listaImagenes
) {

    public static GetTalentoDTO of(TalentoPRUEBA t){
        return new GetTalentoDTO(
                t.getId(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getListaImagenes()
        );
    }

    public GetTalentoDTO(Long id, String titulo, String descripcion){
        this(id, titulo, descripcion, null);
    }

}
