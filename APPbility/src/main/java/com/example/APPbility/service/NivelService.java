package com.example.APPbility.service;

import com.example.APPbility.dto.continente.CreateContinenteCMD;
import com.example.APPbility.dto.nivel.CreateNivelCMD;
import com.example.APPbility.error.custom.EntityWithRelationshipsException;
import com.example.APPbility.error.entity.ContinenteNotFoundException;
import com.example.APPbility.error.entity.NivelNotFoundException;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Nivel;
import com.example.APPbility.repository.NivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelService {

    private final NivelRepository nivelRepository;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------


    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Niveles.
    public Page<Nivel> findAll(Pageable pageable){
        Page<Nivel> result = nivelRepository.findAll(pageable);

        if(result.isEmpty())
            throw new NivelNotFoundException();
        return result;
    }

    //Crear Nivel.
    @Transactional
    public Nivel save(CreateNivelCMD nuevo){
        List<Nivel> listaNiveles = nivelRepository.findListaNivelesOrderedByOrden();

        int numOrden = nuevo.orden();
        int longitudListaNiveles = listaNiveles.size();

        int ultimoNumOrden = numOrden > longitudListaNiveles ? longitudListaNiveles + 1 : numOrden;

        listaNiveles.stream()
            .filter(niv -> niv.getOrden() >= ultimoNumOrden)
            .sorted((a, b) -> Integer.compare(b.getOrden(), a.getOrden()))
            .forEach(niv -> {
                niv.setOrden(niv.getOrden() + 1);
                nivelRepository.save(niv);
            }
        );

        return nivelRepository.save(Nivel.builder()
                .nombre(nuevo.nombre().trim())
                .color(nuevo.color().trim().toUpperCase())
                .orden(nuevo.orden())
                .build()
        );
    }

    //Borrar Nivel.
    public void delete(Long id){
        Nivel nivel = nivelRepository.findById(id).orElseThrow(() -> new NivelNotFoundException(id));

        if(!nivel.getListaTalentos().isEmpty()){
            throw new EntityWithRelationshipsException("No se ha podido eliminar el nivel " +
                "con ID: " + nivel.getId() + " porque tiene talentos asociados.");
        }
        nivelRepository.delete(nivel);

        List<Nivel> listaNiveles = nivelRepository.findListaNivelesOrderedByOrden();

        listaNiveles.stream()
            .filter(niv -> niv.getOrden() > nivel.getOrden())
            .forEach(niv -> {
                niv.setOrden(niv.getOrden() - 1);
                nivelRepository.save(niv);
            }
        );
    }

}
