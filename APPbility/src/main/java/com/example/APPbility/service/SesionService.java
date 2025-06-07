package com.example.APPbility.service;

import com.example.APPbility.dto.sesion.CreateSesionCMD;
import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.IntercambioNotFoundException;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.model.Sesion;
import com.example.APPbility.repository.IntercambioRepository;
import com.example.APPbility.repository.SesionRepository;
import com.example.APPbility.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SesionService {

    private final SesionRepository sesionRepository;
    private final IntercambioRepository intercambioRepository;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Crear Sesión.
    @Transactional
    public Sesion crearSesion(CreateSesionCMD nuevaSesion, Long intercambioId, User usuarioAutenticado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        /*Validación para comprobar si aquel que crea una sesión en el intercambio es o el usuarioDemandante
        o el usuarioSolicitado.*/
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
                !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para crear sesiones en este intercambio.");
        }

        //Validación para comprobar que una sesión sólo se puede asociar a un intercambio con estado PROPUESTO.
        if (!intercambio.getEstado().equals(Estado.ACTIVO)) {
            throw new IllegalMatchException("Solamente se pueden crear sesiones en intercambios con estado ACTIVO.");
        }

        //Validación para comprobar si ya existe una sesión creada con esa fecha en el intercambio.
        boolean SesionConFechaYaExistente =
            sesionRepository.existsByIntercambioIdAndFecha(intercambio.getIntercambioID(), nuevaSesion.fecha());
        if (SesionConFechaYaExistente) {
            throw new IllegalMatchException("Ya existe una sesión creada para la fecha '" + nuevaSesion.fecha() +
                "' en este intercambio.");
        }

        Sesion sesion = Sesion.builder()
            .fecha(nuevaSesion.fecha())
            .intercambio(intercambio)
            .build();

        return sesionRepository.save(sesion);
    }

}
