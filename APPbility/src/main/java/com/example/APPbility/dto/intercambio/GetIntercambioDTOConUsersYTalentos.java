package com.example.APPbility.dto.intercambio;

import com.example.APPbility.dto.mensajeChat.GetMensajeChatDTOConUserID;
import com.example.APPbility.dto.sesion.GetSesionDTOConBloques;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.dto.valoracion.GetValoracionDTOConAmbosUsuariosIDs;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.user.dto.GetUserDTO;

import java.time.LocalDateTime;
import java.util.List;

public record GetIntercambioDTOConUsersYTalentos(
        Long intercambioID,
        Estado estado,
        boolean finalizadoPorDemandante,
        boolean finalizadoPorSolicitado,
        LocalDateTime fechaSolicitud,
        LocalDateTime fechaComienzo,
        LocalDateTime fechaFin,
        GetUserDTO usuarioDemandante,
        GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado,
        GetTalentoDTOConNivel talentoAceptado,
        GetTalentoDTOConNivel talentoSugerido
) {

    public static GetIntercambioDTOConUsersYTalentos of(Intercambio i, GetUserDTO usuarioDemandante,
        GetUserDTO usuarioSolicitado, GetTalentoDTOConNivel talentoSolicitado, GetTalentoDTOConNivel talentoAceptado,
        GetTalentoDTOConNivel talentoSugerido){
        return new GetIntercambioDTOConUsersYTalentos(
                i.getIntercambioID(),
                i.getEstado(),
                i.isFinalizadoPorDemandante(),
                i.isFinalizadoPorSolicitado(),
                i.getFechaSolicitud(),
                i.getFechaComienzo(),
                i.getFechaFin(),
                usuarioDemandante,
                usuarioSolicitado,
                talentoSolicitado,
                talentoAceptado,
                talentoSugerido
        );
    }

}
