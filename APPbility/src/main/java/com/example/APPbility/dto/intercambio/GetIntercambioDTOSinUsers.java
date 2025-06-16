package com.example.APPbility.dto.intercambio;

import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;

import java.time.LocalDateTime;

public record GetIntercambioDTOSinUsers(
        Long intercambioID,
        Estado estado,
        boolean finalizadoPorDemandante,
        boolean finalizadoPorSolicitado,
        LocalDateTime fechaSolicitud,
        LocalDateTime fechaComienzo,
        LocalDateTime fechaFin,
        GetTalentoDTOConNivel talentoSolicitado,
        GetTalentoDTOConNivel talentoAceptado,
        GetTalentoDTOConNivel talentoSugerido
) {

    public static GetIntercambioDTOSinUsers of(Intercambio i, GetTalentoDTOConNivel talentoSolicitado,
        GetTalentoDTOConNivel talentoAceptado, GetTalentoDTOConNivel talentoSugerido){
        return new GetIntercambioDTOSinUsers(
                i.getIntercambioID(),
                i.getEstado(),
                i.isFinalizadoPorDemandante(),
                i.isFinalizadoPorSolicitado(),
                i.getFechaSolicitud(),
                i.getFechaComienzo(),
                i.getFechaFin(),
                talentoSolicitado,
                talentoAceptado,
                talentoSugerido
        );
    }

}
