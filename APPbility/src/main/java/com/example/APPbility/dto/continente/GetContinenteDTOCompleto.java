package com.example.APPbility.dto.continente;

import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.model.Continente;

import java.util.List;

public record GetContinenteDTOCompleto(
        Long id,
        String nombre,
        List<GetPaisDTO> listaPaises
) {

    public static GetContinenteDTOCompleto of(Continente c, List<GetPaisDTO> listaPaises){
        return new GetContinenteDTOCompleto(
                c.getId(),
                c.getNombre(),
                listaPaises
        );
    }

}
