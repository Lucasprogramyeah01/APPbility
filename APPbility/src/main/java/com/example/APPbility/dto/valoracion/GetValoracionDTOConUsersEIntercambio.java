package com.example.APPbility.dto.valoracion;

import com.example.APPbility.dto.intercambio.GetIntercambioDTOSinUsers;
import com.example.APPbility.model.Valoracion;
import com.example.APPbility.user.dto.GetUserDTO;

public record GetValoracionDTOConUsersEIntercambio(
        Long id,
        int puntuacion,
        String titulo,
        String resenha,
        GetUserDTO usuarioEscritor,
        GetUserDTO usuarioValorado,
        GetIntercambioDTOSinUsers intercambio
) {

    public static GetValoracionDTOConUsersEIntercambio of(Valoracion v, GetUserDTO usuarioEscritor,
        GetUserDTO usuarioValorado, GetIntercambioDTOSinUsers intercambio){
        return new GetValoracionDTOConUsersEIntercambio(
                v.getId(),
                v.getPuntuacion(),
                v.getTitulo(),
                v.getResenha(),
                usuarioEscritor,
                usuarioValorado,
                intercambio
        );
    }

}
