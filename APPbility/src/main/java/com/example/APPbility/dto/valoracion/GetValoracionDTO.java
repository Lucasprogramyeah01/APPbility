package com.example.APPbility.dto.valoracion;

import com.example.APPbility.model.Valoracion;

public record GetValoracionDTO(
        Long id,
        int puntuacion,
        String titulo,
        String resenha
) {

    public static GetValoracionDTO of(Valoracion v){
        return new GetValoracionDTO(
                v.getId(),
                v.getPuntuacion(),
                v.getTitulo(),
                v.getResenha()
        );
    }

}
