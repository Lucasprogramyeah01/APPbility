package com.example.APPbility.dto.nivel;

import com.example.APPbility.model.Nivel;

public record GetNivelDTO(
        Long id,
        String nombre,
        String color,
        int orden
) {

    public static GetNivelDTO of(Nivel n){
        return new GetNivelDTO(
                n.getId(),
                n.getNombre(),
                n.getColor(),
                n.getOrden()
        );
    }

}
