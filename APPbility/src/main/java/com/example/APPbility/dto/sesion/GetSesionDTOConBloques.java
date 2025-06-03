package com.example.APPbility.dto.sesion;

import com.example.APPbility.dto.bloque.GetBloqueDTOConUserID;
import com.example.APPbility.model.Sesion;

import java.time.LocalDate;
import java.util.List;

public record GetSesionDTOConBloques(
        Long id,
        LocalDate fecha,
        List<GetBloqueDTOConUserID> listaBloques
) {

    public static GetSesionDTOConBloques of(Sesion s, List<GetBloqueDTOConUserID> listaBloques){
        return new GetSesionDTOConBloques(
                s.getId(),
                s.getFecha(),
                listaBloques
        );
    }

}
