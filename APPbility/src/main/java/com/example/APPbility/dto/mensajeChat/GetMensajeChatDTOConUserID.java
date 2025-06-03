package com.example.APPbility.dto.mensajeChat;

import com.example.APPbility.model.MensajeChat;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetMensajeChatDTOConUserID(
        Long id,
        String contenido,
        LocalDateTime fechaEnvio,
        UUID usuarioID
) {

    public static GetMensajeChatDTOConUserID of(MensajeChat mc, UUID usuarioID){
        return new GetMensajeChatDTOConUserID(
                mc.getId(),
                mc.getContenido(),
                mc.getFechaEnvio(),
                usuarioID
        );
    }

}
