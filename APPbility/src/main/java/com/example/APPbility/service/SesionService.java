package com.example.APPbility.service;

import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.IntercambioNotFoundException;
import com.example.APPbility.error.entity.SesionNotFoundException;
import com.example.APPbility.model.*;
import com.example.APPbility.repository.BloqueRepository;
import com.example.APPbility.repository.IntercambioRepository;
import com.example.APPbility.repository.SesionRepository;
import com.example.APPbility.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SesionService {

    private final SesionRepository sesionRepository;
    private final BloqueRepository bloqueRepository;
    private final IntercambioRepository intercambioRepository;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public List<Bloque> findBloquesBySesionId(Long id){
        return bloqueRepository.findBloquesBySesionId(id);
    }

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todas las Sesiones de un Intercambio.
    public List<Sesion> findSesionesFromIntercambio(Long intercambioID, User usuarioAutenticado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioID)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioID));

        /*Validación para comprobar si aquel que crea una sesión en el intercambio es o el usuarioDemandante
        o el usuarioSolicitado.*/
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
                !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para ver las sesiones de este intercambio.");
        }

        List<Sesion> result = sesionRepository.findAllByIntercambioIdOrderByFechaAsc(intercambioID);

        if(result.isEmpty())
            throw new SesionNotFoundException();
        return result;
    }

}
