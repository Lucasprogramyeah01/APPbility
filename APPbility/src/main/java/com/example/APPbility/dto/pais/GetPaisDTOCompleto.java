package com.example.APPbility.dto.pais;

import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.model.Pais;
import com.example.APPbility.user.dto.GetUserSinPaisNativoDTO;
import com.example.APPbility.user.dto.GetUserSinPaisResidenciaDTO;

import java.util.List;

public record GetPaisDTOCompleto(
        Long id,
        String nombre,
        String codigoISO,
        String bandera,
        GetContinenteDTO continente,
        List<GetUserSinPaisNativoDTO> listaUsuariosNativos,
        List<GetUserSinPaisResidenciaDTO> listaUsuariosResidentes
) {

    public static GetPaisDTOCompleto of (Pais p, GetContinenteDTO continente, List<GetUserSinPaisNativoDTO> listaUsuariosNativos,
        List<GetUserSinPaisResidenciaDTO> listaUsuariosResidentes){
        return new GetPaisDTOCompleto(
                p.getId(),
                p.getNombre(),
                p.getCodigoISO(),
                p.getBandera(),
                continente,
                listaUsuariosNativos,
                listaUsuariosResidentes
        );
    }

}
