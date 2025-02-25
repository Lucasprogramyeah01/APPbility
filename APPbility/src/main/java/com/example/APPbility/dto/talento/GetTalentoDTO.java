package com.example.APPbility.dto.talento;

import com.example.APPbility.model.Talento;

import java.util.List;

public record GetTalentoDTO(
        String titulo,
        String descripcion,
        List<String> listaImagenes
) {

    public static GetTalentoDTO of(Talento t){
        return new GetTalentoDTO(
                t.getTitulo(),
                t.getDescripcion(),
                t.getListaImagenes()
        );
    }

}
