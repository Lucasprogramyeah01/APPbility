package com.example.APPbility.service;

import com.example.APPbility.dto.valoracion.CreateValoracionCMD;
import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.IntercambioNotFoundException;
import com.example.APPbility.error.entity.TalentoNotFoundException;
import com.example.APPbility.error.entity.ValoracionNotFoundException;
import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.model.Talento;
import com.example.APPbility.model.Valoracion;
import com.example.APPbility.repository.IntercambioRepository;
import com.example.APPbility.repository.ValoracionRepository;
import com.example.APPbility.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;
    private final IntercambioRepository intercambioRepository;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Crear Valoración.
    @Transactional
    public Valoracion crearValoracion(Long intercambioID, CreateValoracionCMD valoracionCMD, User usuarioAutenticado) {
        Intercambio intercambio = intercambioRepository.findById(intercambioID)
                .orElseThrow(() -> new IntercambioNotFoundException(intercambioID));

        //Validación para comprobar que una valoración sólo se puede asociar a un intercambio con estado FINALIZADO.
        if (!intercambio.getEstado().equals(Estado.FINALIZADO)) {
            throw new IllegalMatchException("Solamente se pueden realizar valoraciones en un intercambio si su " +
                    "estado es FINALIZADO.");
        }

        /*Validación para comprobar si aquel que valora a otro usuario en el intercambio es o el usuarioDemandante
        o el usuarioSolicitado.*/
        if (!intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId()) &&
                !intercambio.getUsuarioSolicitado().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para realizar una valoración en este intercambio.");
        }

        /*Validación para comprobar si el usuario que está intentado valorar a otro en el intercambio, no lo ha hecho
        anteriormente.*/
        boolean yaValorado = valoracionRepository.existsByIntercambioIDAndUsuarioEscritorID(intercambioID, usuarioAutenticado.getId());
        if (yaValorado) {
            throw new IllegalMatchException("Ya ha realizado una valoración en este intercambio.");
        }

        User usuarioValorado = intercambio.getUsuarioDemandante().getId().equals(usuarioAutenticado.getId())
                ? intercambio.getUsuarioSolicitado() : intercambio.getUsuarioDemandante();

        Valoracion valoracion = Valoracion.builder()
                .puntuacion(valoracionCMD.puntuacion())
                .titulo(valoracionCMD.titulo() != null ? valoracionCMD.titulo().trim() : null)
                .resenha(valoracionCMD.resenha().trim())
                .usuarioEscritor(usuarioAutenticado)
                .usuarioValorado(usuarioValorado)
                .intercambio(intercambio)
                .build();

        return valoracionRepository.save(valoracion);
    }

    //Validación para comprobar si aquel que va a editar la valoración es el usuarioEscritor.
    public Page<Valoracion> findValoracionesFromUsuario(UUID id, Pageable pageable) {
        Page<Valoracion> result = valoracionRepository.findAllValoracionesByUsuarioID(id, pageable);

        if (result.isEmpty())
            throw new ValoracionNotFoundException();
        return result;
    }

    //Editar Valoración.
    /*En este métod0 se utiliza el CreateValoracionCMD como si fuera un "EditValoracionCMD" porque ambos serían el mismo
    DTO, por lo que CreateValoracionCMD vale tanto para una cosa como para otra.*/
    @Transactional
    public Valoracion editarValoracion(Long valoracionID, CreateValoracionCMD valoracionCMD, User usuarioAutenticado) {
        Valoracion valoracion = valoracionRepository.findById(valoracionID)
                .orElseThrow(() -> new ValoracionNotFoundException(valoracionID));

        // Solo puede editar el autor
        if (!valoracion.getUsuarioEscritor().getId().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException("No tiene permiso para editar esta valoración.");
        }

        //Validación para comprobar que una valoración sólo se puede asociar a un intercambio con estado FINALIZADO.
        if (!valoracion.getIntercambio().getEstado().equals(Estado.FINALIZADO)) {
            throw new IllegalMatchException("Solamente se pueden editar valoraciones de intercambios con estado FINALIZADO.");
        }

        valoracion.setPuntuacion(valoracionCMD.puntuacion());
        valoracion.setTitulo(valoracionCMD.titulo() != null ? valoracionCMD.titulo().trim() : null);
        valoracion.setResenha(valoracionCMD.resenha().trim());

        return valoracionRepository.save(valoracion);
    }

}
