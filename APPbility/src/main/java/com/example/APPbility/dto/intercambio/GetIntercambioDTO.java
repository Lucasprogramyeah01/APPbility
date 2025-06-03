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

public record GetIntercambioDTO(
        Long intercambioID,
        LocalDateTime fechaSolicitud,
        LocalDateTime fechaComienzo,
        LocalDateTime fechaFin,
        Estado estado,
        boolean finalizadoPorDemandante,
        boolean finalizadoPorSolicitado,
        GetUserDTO usuarioDemandante,
        GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado,
        GetTalentoDTOConNivel talentoSugerido,
        GetTalentoDTOConNivel talentoAceptado,
        List<GetSesionDTOConBloques> listaSesiones,
        List<GetMensajeChatDTOConUserID> listaMensajesChat,
        List<GetValoracionDTOConAmbosUsuariosIDs> listaValoraciones
) {

    public static GetIntercambioDTO of(Intercambio i, GetUserDTO usuarioDemandante, GetUserDTO usuarioSolicitado,
        GetTalentoDTOConNivel talentoSolicitado, GetTalentoDTOConNivel talentoSugerido,
        GetTalentoDTOConNivel talentoAceptado, List<GetSesionDTOConBloques> listaSesiones,
        List<GetMensajeChatDTOConUserID> listaMensajesChat, List<GetValoracionDTOConAmbosUsuariosIDs> listaValoraciones){
        return new GetIntercambioDTO(
                i.getIntercambioID(),
                i.getFechaSolicitud(),
                i.getFechaComienzo(),
                i.getFechaFin(),
                i.getEstado(),
                i.isFinalizadoPorDemandante(),
                i.isFinalizadoPorSolicitado(),
                usuarioDemandante,
                usuarioSolicitado,
                talentoSolicitado,
                talentoSugerido,
                talentoAceptado,
                listaSesiones,
                listaMensajesChat,
                listaValoraciones
        );
    }

}
