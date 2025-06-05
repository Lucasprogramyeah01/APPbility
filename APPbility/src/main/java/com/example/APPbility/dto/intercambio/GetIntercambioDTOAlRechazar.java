package com.example.APPbility.dto.intercambio;

import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.user.dto.GetUserDTO;

import java.time.LocalDateTime;

public record GetIntercambioDTOAlRechazar(
        Long intercambioID,
        Estado estado,
        LocalDateTime fechaSolicitud,
        GetUserDTO usuarioDemandante,
        GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado,
        GetTalentoDTOConNivel talentoSugerido
) {

    public static GetIntercambioDTOAlRechazar of(Intercambio i, GetUserDTO usuarioDemandante, GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado, GetTalentoDTOConNivel talentoSugerido){
        return new GetIntercambioDTOAlRechazar(
                i.getIntercambioID(),
                i.getEstado(),
                i.getFechaSolicitud(),
                usuarioDemandante,
                usuarioSolicitado,
                talentoSolicitado,
                talentoSugerido
        );
    }

}
