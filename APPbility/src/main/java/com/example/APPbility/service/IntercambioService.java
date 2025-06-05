package com.example.APPbility.service;

import com.example.APPbility.dto.intercambio.AceptarIntercambioCMD;
import com.example.APPbility.dto.intercambio.CreateIntercambioCMD;
import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.IntercambioNotFoundException;
import com.example.APPbility.error.entity.NivelNotFoundException;
import com.example.APPbility.error.entity.TalentoNotFoundException;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.model.Nivel;
import com.example.APPbility.model.Talento;
import com.example.APPbility.repository.IntercambioRepository;
import com.example.APPbility.repository.TalentoRepository;
import com.example.APPbility.user.error.UserNotFoundException;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntercambioService {

    private final IntercambioRepository intercambioRepository;
    private final UserRepository userRepository;
    private final TalentoRepository talentoRepository;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------


    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Proponer Intercambio.
    public Intercambio proponerIntercambio(CreateIntercambioCMD intercambioPropuesto, User usuarioDemandante){
        User usuarioSolicitado = userRepository.findById(intercambioPropuesto.usuarioSolicitadoID())
            .orElseThrow(() -> new UserNotFoundException(intercambioPropuesto.usuarioSolicitadoID()));

        Talento talentoSolicitado = talentoRepository.findById(intercambioPropuesto.talentoSolicitadoID())
            .orElseThrow(() -> new TalentoNotFoundException(intercambioPropuesto.talentoSolicitadoID()));

        Talento talentoSugerido = talentoRepository.findById(intercambioPropuesto.talentoSugeridoID())
            .orElseThrow(() -> new TalentoNotFoundException(intercambioPropuesto.talentoSugeridoID()));

        /*Validación para comprobar si ya existe un intercambio PROPUESTO o ACTIVO entre el usuarioDemandante y el
        usuarioSolicitado (Un usuario no puede tener más de un intercambio PROPUESTO o ACTIVO con la misma persona).*/
        boolean intercambioEntreUsuariosYaExistente = intercambioRepository.existsIntercambioEntreUsuariosConEstados(
            usuarioDemandante, usuarioSolicitado, List.of(Estado.PROPUESTO, Estado.ACTIVO)
        );
        if(intercambioEntreUsuariosYaExistente){
            throw new UnauthorizedAccessException("Actualmente existe un intercambio PROPUESTO o ACTIVO con ese usuario en su perfil.");
        }

        //Validación para comprobar si el usuario solicitado es un ADMIN.
        if(usuarioSolicitado.getRoles().stream().anyMatch(r -> r.getPalabra().equals("ADMIN"))){
            throw new IllegalMatchException("No se puede realizar un intercambio con un administrador.");
        }

        //Validación para comprobar si el talento solicitado pertenece al usuario solicitado.
        if (!talentoSolicitado.getUsuario().getId().equals(usuarioSolicitado.getId())) {
            throw new IllegalMatchException("El talento solicitado no pertenece al usuario que se ha solicitado para realizar el intercambio.");
        }

        //Validación para comprobar si el talento sugerido pertenece al usuario demandante.
        if (!talentoSugerido.getUsuario().getId().equals(usuarioDemandante.getId())) {
            throw new IllegalMatchException("El talento sugerido no pertenece al usuario que está creando el intercambio.");
        }

        LocalDateTime fechaSolicitud = LocalDateTime.now();

        return intercambioRepository.save(Intercambio.builder()
                .fechaSolicitud(fechaSolicitud)
                .usuarioDemandante(usuarioDemandante)
                .usuarioSolicitado(usuarioSolicitado)
                .talentoSolicitado(talentoSolicitado)
                .talentoSugerido(talentoSugerido)
                .estado(Estado.PROPUESTO)
                .build()
        );
    }

    //Aceptar Intercambio.
    public Intercambio aceptarIntercambio(Long intercambioId, AceptarIntercambioCMD intercambioCMD, User usuarioSolicitado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
            .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        //Validación para comprobar si aquel que acepta el intercambio es el usuarioSolicitado.
        if (!intercambio.getUsuarioSolicitado().getId().equals(usuarioSolicitado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para aceptar este intercambio.");
        }

        //Validación para comprobar si el estado del talento que se desea aceptar es PROPUESTO.
        if (!intercambio.getEstado().equals(Estado.PROPUESTO)) {
            throw new IllegalMatchException("Solamente se pueden aceptar intercambios con estado PROPUESTO.");
        }

        Talento talentoAceptado = talentoRepository.findById(intercambioCMD.talentoAceptadoID())
            .orElseThrow(() -> new TalentoNotFoundException(intercambioCMD.talentoAceptadoID()));

        //Validación para comprobar si el talento aceptado pertenece al usuario demandante.
        if (!talentoAceptado.getUsuario().getId().equals(intercambio.getUsuarioDemandante().getId())) {
            throw new IllegalMatchException("El talento seleccionado no pertenece al usuario que ha sugerido el intercambio.");
        }

        intercambio.setTalentoAceptado(talentoAceptado);
        intercambio.setEstado(Estado.ACTIVO);
        intercambio.setFechaComienzo(LocalDateTime.now());

        return intercambioRepository.save(intercambio);
    }

    //Rechazar Intercambio.
    public Intercambio rechazarIntercambio(Long intercambioId, User usuarioSolicitado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        //Validación para comprobar si aquel que rechaza el intercambio es el usuarioSolicitado.
        if (!intercambio.getUsuarioSolicitado().getId().equals(usuarioSolicitado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para rechazar este intercambio.");
        }

        //Validación para comprobar si el estado del talento que se desea rechazar es PROPUESTO.
        if (!intercambio.getEstado().equals(Estado.PROPUESTO)) {
            throw new IllegalMatchException("Solo se pueden rechazar intercambios con estado PROPUESTO.");
        }

        intercambio.setEstado(Estado.RECHAZADO);
        return intercambioRepository.save(intercambio);
    }


}
