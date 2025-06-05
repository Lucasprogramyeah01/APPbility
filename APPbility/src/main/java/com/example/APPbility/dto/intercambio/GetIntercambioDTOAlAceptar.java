package com.example.APPbility.dto.intercambio;

import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.user.dto.GetUserDTO;

import java.time.LocalDateTime;

public record GetIntercambioDTOAlAceptar(
        Long intercambioID,
        Estado estado,
        LocalDateTime fechaSolicitud,
        LocalDateTime fechaComienzo,
        GetUserDTO usuarioDemandante,
        GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado,
        GetTalentoDTOConNivel talentoAceptado
) {

    public static GetIntercambioDTOAlAceptar of(Intercambio i, GetUserDTO usuarioDemandante, GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado, GetTalentoDTOConNivel talentoAceptado){
        return new GetIntercambioDTOAlAceptar(
                i.getIntercambioID(),
                i.getEstado(),
                i.getFechaSolicitud(),
                i.getFechaComienzo(),
                usuarioDemandante,
                usuarioSolicitado,
                talentoSolicitado,
                talentoAceptado
        );
    }

}
