package com.example.APPbility.dto.sesion;

import com.example.APPbility.model.Sesion;

import java.time.LocalDate;

public record GetSesionDTO(
        Long id,
        LocalDate fecha
) {

    public static GetSesionDTO of(Sesion s){
        return new GetSesionDTO(
                s.getId(),
                s.getFecha()
        );
    }

}
