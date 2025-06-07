package com.example.APPbility.service;

import com.example.APPbility.dto.intercambio.AceptarIntercambioCMD;
import com.example.APPbility.dto.intercambio.CreateIntercambioCMD;
import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.IntercambioNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntercambioService {

    private final IntercambioRepository intercambioRepository;
    private final UserRepository userRepository;
    private final TalentoRepository talentoRepository;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Proponer Intercambio.
    @Transactional
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

    //Cancelar Intercambio Propuesto.
    @Transactional
    public void cancelarIntercambioPropuesto(Long intercambioId, User usuarioDemandante) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        //Validación para comprobar si aquel que cancela el intercambio propuesto es el usuarioDemandante.
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioDemandante.getId())) {
            throw new UnauthorizedAccessException("Solamente el usuario que ha propuesto el intercambio puede " +
            "cancelarlo.");
        }

        //Validación para comprobar si el estado del intercambio que se desea cancelar es PROPUESTO.
        if (!intercambio.getEstado().equals(Estado.PROPUESTO)) {
            throw new IllegalMatchException("Solamente se pueden cancelar intercambios con estado PROPUESTO.");
        }

        intercambioRepository.delete(intercambio);
    }

    //Aceptar Intercambio.
    @Transactional
    public Intercambio aceptarIntercambio(Long intercambioId, AceptarIntercambioCMD intercambioCMD, User usuarioSolicitado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
            .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        //Validación para comprobar si aquel que acepta el intercambio es el usuarioSolicitado.
        if (!intercambio.getUsuarioSolicitado().getId().equals(usuarioSolicitado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para aceptar este intercambio.");
        }

        //Validación para comprobar si el estado del intercambio que se desea aceptar es PROPUESTO.
        if (!intercambio.getEstado().equals(Estado.PROPUESTO)) {
            throw new IllegalMatchException("Solamente se pueden aceptar intercambios con estado PROPUESTO.");
        }

        Talento talentoAceptado = talentoRepository.findById(intercambioCMD.talentoAceptadoID())
            .orElseThrow(() -> new TalentoNotFoundException(intercambioCMD.talentoAceptadoID()));

        //Validación para comprobar si el intercambio aceptado pertenece al usuario demandante.
        if (!talentoAceptado.getUsuario().getId().equals(intercambio.getUsuarioDemandante().getId())) {
            throw new IllegalMatchException("El talento seleccionado no pertenece al usuario que ha sugerido el intercambio.");
        }

        intercambio.setTalentoAceptado(talentoAceptado);
        intercambio.setEstado(Estado.ACTIVO);
        intercambio.setFechaComienzo(LocalDateTime.now());

        return intercambioRepository.save(intercambio);
    }

    //Rechazar Intercambio.
    @Transactional
    public Intercambio rechazarIntercambio(Long intercambioId, User usuarioSolicitado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        //Validación para comprobar si aquel que rechaza el intercambio es el usuarioSolicitado.
        if (!intercambio.getUsuarioSolicitado().getId().equals(usuarioSolicitado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para rechazar este intercambio.");
        }

        //Validación para comprobar si el estado del intercambio que se desea rechazar es PROPUESTO.
        if (!intercambio.getEstado().equals(Estado.PROPUESTO)) {
            throw new IllegalMatchException("Solamente se pueden rechazar intercambios con estado PROPUESTO.");
        }

        intercambio.setEstado(Estado.RECHAZADO);
        return intercambioRepository.save(intercambio);
    }

    //Ver detalles de Intercambio.
    public Intercambio verDetallesDeIntercambio(Long id, User usuarioAutenticado) {
        Intercambio intercambio = intercambioRepository.findById(id)
                .orElseThrow(() -> new IntercambioNotFoundException(id));

        /*Validación para comprobar si aquel que accede a los detalles del intercambio es o el usuarioDemandante
        o el usuarioSolicitado.*/
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
            !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para ver los detalles de este intercambio.");
        }

        return intercambio;
    }

    //Listar todos los Intercambio de un Usuario.
    public Page<Intercambio> findIntercambiosFromUsuario(User usuarioAutenticado, Pageable pageable) {
        Page<Intercambio> result =
            intercambioRepository.findByUsuarioDemandanteIdOrUsuarioSolicitadoId(usuarioAutenticado.getId(), pageable);

        if(result.isEmpty())
            throw new TalentoNotFoundException();
        return result;
    }

    //Finalizar Intercambio.
    @Transactional
    public Intercambio finalizarIntercambio(Long intercambioId, User usuarioAutenticado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        /*Validación para comprobar si aquel que da por finalizado el intercambio es o el usuarioDemandante
        o el usuarioSolicitado.*/
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
                !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para finalizar este intercambio.");
        }

        //Validación para comprobar si el estado del intercambio que se desea finalizar es ACTIVO.
        if (!intercambio.getEstado().equals(Estado.ACTIVO)) {
            throw new IllegalMatchException("Solamente se pueden finalizar intercambios con estado ACTIVO.");
        }

        if (intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId())) {
            intercambio.setFinalizadoPorDemandante(true);
        } else {
            intercambio.setFinalizadoPorSolicitado(true);
        }

        if (intercambio.isFinalizadoPorDemandante() && intercambio.isFinalizadoPorSolicitado()) {
            intercambio.setEstado(Estado.FINALIZADO);
            intercambio.setFechaFin(LocalDateTime.now());
        }

        return intercambioRepository.save(intercambio);
    }

    //Deshacer Finalización de Intercambio por parte de un Usuario.
    @Transactional
    public Intercambio deshacerFinalizacionDeIntercambioPorUsuario(Long intercambioId, User usuarioAutenticado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioId));

        /*Validación para comprobar si aquel que cancela la finalización del intercambio por su parte es o el
        usuarioDemandante o el usuarioSolicitado.*/
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
                !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para cancelar la finalización este intercambio.");
        }

        /*Validación para comprobar que no se pueda deshacer la finalización del intercambio si se encuentra en estado
        FINALIZADO.*/
        if (intercambio.getEstado().equals(Estado.FINALIZADO)) {
            throw new IllegalMatchException("El estado de este intercambio es FINALIZADO, por lo que ya no se puede deshacer."
            );
        }

        /*Validación para comprobar si el estado del intercambio del que se desea cancelar la finalización por parte de
        el usuario autenticado es ACTIVO.*/
        if (!intercambio.getEstado().equals(Estado.ACTIVO)) {
            throw new IllegalMatchException("Solamente se puede deshacer la finalización de un intercambio si su estado es ACTIVO.");
        }

        if (intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId())) {
            intercambio.setFinalizadoPorDemandante(false);
        } else {
            intercambio.setFinalizadoPorSolicitado(false);
        }

        return intercambioRepository.save(intercambio);
    }


}
