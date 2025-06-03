package com.example.APPbility.service;

import com.example.APPbility.dto.intercambio.CreateIntercambioCMD;
import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.TalentoNotFoundException;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
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
        usuarioSolicitado (Un usuario no puede tener más de un intercambio PROPUESTO o ACTIVO con la misma persona.*/
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
            throw new IllegalMatchException("El talento solicitado no pertenece al usuario solicitado para realizar el intercambio.");
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



}
