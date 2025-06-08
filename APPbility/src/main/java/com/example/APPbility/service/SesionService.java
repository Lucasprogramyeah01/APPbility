package com.example.APPbility.service;

import com.example.APPbility.error.custom.IllegalMatchException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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

    //Eliminar Sesion.
    @Transactional
    public void eliminarSesion(Long sesionID, User usuarioAutenticado) {
        Sesion sesion = sesionRepository.findById(sesionID).orElseThrow(() -> new SesionNotFoundException(sesionID));

        Intercambio intercambio = sesion.getIntercambio();

        // Validar que el usuario pertenece al intercambio
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
            !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para eliminar esta sesión.");
        }

        // Validar que el intercambio está activo
        if (!intercambio.getEstado().equals(Estado.ACTIVO)) {
            throw new IllegalMatchException("Solo se pueden eliminar sesiones de intercambios activos.");
        }

        // Validar que la fecha no haya pasado
        if (sesion.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalMatchException("No se puede eliminar una sesión cuya fecha ya ha pasado.");
        }

        // Validar si la sesión es hoy y algún bloque ya pasó su hora
        if (sesion.getFecha().isEqual(LocalDate.now())) {
            boolean bloqueYaRealizado = sesion.getListaBloques().stream().anyMatch(b -> b.getHora().isBefore(LocalTime.now()));
            if (bloqueYaRealizado) {
                throw new IllegalMatchException("No se puede eliminar una sesión con bloques cuya hora ya ha pasado.");
            }
        }

        sesionRepository.delete(sesion);
    }

}
