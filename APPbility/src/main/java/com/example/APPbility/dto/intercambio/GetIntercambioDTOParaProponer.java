package com.example.APPbility.dto.intercambio;

import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.user.dto.GetUserDTO;

import java.time.LocalDateTime;

public record GetIntercambioDTOParaProponer(
        Long intercambioID,
        LocalDateTime fechaSolicitud,
        Estado estado,
        GetUserDTO usuarioDemandante,
        GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado,
        GetTalentoDTOConNivel talentoSugerido
) {

    public static GetIntercambioDTOParaProponer of(Intercambio i, GetUserDTO usuarioDemandante,
        GetUserDTO usuarioSolicitado, GetTalentoDTOConNivel talentoSolicitado, GetTalentoDTOConNivel talentoSugerido){
        return new GetIntercambioDTOParaProponer(
                i.getIntercambioID(),
                i.getFechaSolicitud(),
                i.getEstado(),
                usuarioDemandante,
                usuarioSolicitado,
                talentoSolicitado,
                talentoSugerido
        );
    }

}
