package com.example.APPbility.service;

import com.example.APPbility.dto.bloque.CreateBloqueCMD;
import com.example.APPbility.dto.bloque.EditBloqueCMD;
import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.BloqueNotFoundException;
import com.example.APPbility.error.entity.IntercambioNotFoundException;
import com.example.APPbility.model.Bloque;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.model.Sesion;
import com.example.APPbility.repository.BloqueRepository;
import com.example.APPbility.repository.IntercambioRepository;
import com.example.APPbility.repository.SesionRepository;
import com.example.APPbility.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class BloqueService {

    private final BloqueRepository bloqueRepository;
    private final SesionRepository sesionRepository;
    private final IntercambioRepository intercambioRepository;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Crear Bloque.
    @Transactional
    public Bloque crearBloque(CreateBloqueCMD nuevoBloque, User usuarioAutenticado) {
        Intercambio intercambio = intercambioRepository.findById(nuevoBloque.intercambioID())
            .orElseThrow(() -> new IntercambioNotFoundException(nuevoBloque.intercambioID()));

        /*Validación para comprobar si aquel que crea un bloque para una sesión en el intercambio es o el
        usuarioDemandante o el usuarioSolicitado.*/
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
            !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para añadir bloques a esta sesión.");
        }

        /*Validación para comprobar que un bloque sólo se puede asociar a una sesión de un intercambio con
        estado ACTIVO.*/
        if (!intercambio.getEstado().equals(Estado.ACTIVO)) {
            throw new IllegalMatchException("Solamente se pueden crear bloques en sesiones de intercambios con " +
                "estado ACTIVO.");
        }

        //Se busca o se crea una sesión dependiendo del caso.
        Sesion sesion =
            sesionRepository.findSesionByFechaAndIntercambioID(nuevoBloque.fechaSesion(), intercambio.getIntercambioID())
                .orElseGet(() -> {
                    Sesion nuevaSesion = Sesion.builder()
                        .fecha(nuevoBloque.fechaSesion())
                        .intercambio(intercambio)
                        .build();
                    return sesionRepository.save(nuevaSesion);
                });

        //Validación para comprobar si la sesión tiene 2 bloques ya asignados.
        if (sesion.getListaBloques().size() >= 2) {
            throw new IllegalMatchException("Esta sesión ya tiene 2 bloques asignados.");
        }

        /*Validación para comprobar si el usuario que está intentado crear el bloque para esa sesión ya ha creado
        uno anteriormente.*/
        boolean bloqueDeUsuarioYaExistente = sesion.getListaBloques().stream()
            .anyMatch(b -> b.getUsuario().getId().equals(usuarioAutenticado.getId()));
        if (bloqueDeUsuarioYaExistente) {
            throw new IllegalMatchException("Ya ha creado un bloque para esta sesión.");
        }

        //Validación para comprobar que la hora asignada al bloque sea coherente.
        if (sesion.getFecha().isEqual(LocalDate.now())) {
            if (nuevoBloque.hora().isBefore(LocalTime.now())) {
                throw new IllegalMatchException("No se puede asignar una hora anterior a la hora actual para la sesión de hoy.");
            }
        } else if (sesion.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalMatchException("No se puede crear un bloque para una sesión con fecha pasada.");
        }

        Bloque bloque = Bloque.builder()
            .titulo(nuevoBloque.titulo().trim())
            .descripcion(nuevoBloque.descripcion().trim())
            .hora(nuevoBloque.hora())
            .usuario(usuarioAutenticado)
            .sesion(sesion)
            .build();

        return bloqueRepository.save(bloque);
    }

    //Editar Bloque.
    @Transactional
    public Bloque editarBloque(Long bloqueID, EditBloqueCMD bloqueCMD, User usuarioAutenticado) {
        Bloque bloque = bloqueRepository.findById(bloqueID).orElseThrow(() -> new BloqueNotFoundException(bloqueID));

        Sesion sesion = bloque.getSesion();
        Intercambio intercambio = sesion.getIntercambio();

        //Validación para comprobar si aquel que edita un bloque es el usuario que lo creó.
        if (!bloque.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para editar este bloque.");
        }

        //Validación para comprobar que el bloque de una sesión sólo se puede asociar a un intercambio con estado ACTIVO.
        if (!intercambio.getEstado().equals(Estado.ACTIVO)) {
            throw new IllegalMatchException("Solamente se pueden editar bloques de intercambios con estado ACTIVO.");
        }

        /*Validación para comprobar que el bloque de una sesión sólo se puede asociar a una sesión cuya fecha
        establecida es posterior a la fecha actual.*/
        if (sesion.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalMatchException("No se puede editar un bloque de una sesión ya concluida.");
        }

        //Validación para comprobar que la hora asignada al bloque sea coherente.
        if (sesion.getFecha().isEqual(LocalDate.now()) && bloqueCMD.hora().isBefore(LocalTime.now())) {
            throw new IllegalMatchException("No se puede asignar una hora anterior a la hora actual para la sesión de hoy.");
        }

        bloque.setTitulo(bloqueCMD.titulo().trim());
        bloque.setDescripcion(bloqueCMD.descripcion().trim());
        bloque.setHora(bloqueCMD.hora());

        return bloqueRepository.save(bloque);
    }

    //Eliminar Bloque.
    @Transactional
    public void eliminarBloque(Long bloqueID, User usuarioAutenticado) {
        Bloque bloque = bloqueRepository.findById(bloqueID).orElseThrow(() -> new BloqueNotFoundException(bloqueID));

        Sesion sesion = bloque.getSesion();
        Intercambio intercambio = sesion.getIntercambio();

        //Validación para comprobar si aquel que edita un bloque es el usuario que lo creó.
        if (!bloque.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para eliminar este bloque.");
        }

        //Validación para comprobar que el bloque de una sesión sólo se puede eliminar de un intercambio con estado ACTIVO.
        if (!intercambio.getEstado().equals(Estado.ACTIVO)) {
            throw new IllegalMatchException("Solamente se pueden editar bloques de intercambios con estado ACTIVO.");
        }

        /*Validación para comprobar que el bloque de una sesión sólo se puede eliminar de una sesión cuya fecha
        establecida es posterior a la fecha actual.*/
        if (sesion.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalMatchException("No se puede eliminar un bloque de una sesión ya concluida.");
        }

        /*Validación para comprobar que si la hora del bloque que se desea eliminar ya ha pasado, este no se pueda
        borrar (si la sesión estaba establecida para hoy y ya ha pasado la hora del bloque, se supone que este se
        ha tenido que llevar a cabo).*/
        if (sesion.getFecha().isEqual(LocalDate.now()) && bloque.getHora().isBefore(LocalTime.now())) {
            throw new IllegalMatchException("No se puede eliminar un bloque cuya hora ya ha pasado.");
        }

        bloqueRepository.delete(bloque);

        //Si no quedan más bloques en la sesión, se elimina la sesión también.
        if (sesion.getListaBloques().size() <= 1) {
            sesionRepository.delete(sesion);
        }
    }

}
