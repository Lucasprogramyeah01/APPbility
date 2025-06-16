package com.example.APPbility.service;

import com.example.APPbility.dto.continente.CreateContinenteCMD;
import com.example.APPbility.dto.continente.EditContinenteCMD;
import com.example.APPbility.error.custom.DuplicatedAttributeException;
import com.example.APPbility.error.custom.EntityWithRelationshipsException;
import com.example.APPbility.error.entity.ContinenteNotFoundException;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import com.example.APPbility.repository.ContinenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContinenteService {

    private final ContinenteRepository continenteRepository;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public List<Pais> getListaPaisesByContinenteID(Long id){
        return continenteRepository.findListaPaisesByContinenteID(id);
    }

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Continentes.
    public Page<Continente> findAll(Pageable pageable){
        Page<Continente> result = continenteRepository.findAll(pageable);

        if(result.isEmpty())
            throw new ContinenteNotFoundException();
        return result;
    }

    //Buscar Continente por ID.
    public Continente findById(Long id){
        return continenteRepository.findById(id).orElseThrow(() -> new ContinenteNotFoundException(id));
    }

    //Crear Continente.
    @Transactional
    public Continente save(CreateContinenteCMD nuevo){
        return continenteRepository.save(Continente.builder()
            .nombre(nuevo.nombre())
            .build());
    }

    //Editar Continente.
    @Transactional
    public Continente edit(EditContinenteCMD editContinenteCMD, Long id){
        Continente continente = continenteRepository.findById(id).orElseThrow(() ->
            new ContinenteNotFoundException(id));

        //Alternativa de la validación "UniqueNombreContinenteEdit".
        boolean nombreDuplicado = continenteRepository.existsByNombreIgnoreCaseAndIdNot(editContinenteCMD.nombre().trim(), id);
        if (nombreDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un continente registrado con ese nombre.");
        }

        continente.setNombre(editContinenteCMD.nombre().trim());

        return continenteRepository.save(continente);
    }

    //Borrar Continente.
    @Transactional
    public void delete(Long id){
        Continente continente = continenteRepository.findById(id).orElseThrow(() ->
            new ContinenteNotFoundException(id));

        if (!continente.getListaPaises().isEmpty()) {
            throw new EntityWithRelationshipsException("No se ha podido eliminar el continente " +
                "con ID: " + continente.getId() + " porque tiene países asociados.");
        }
        continenteRepository.delete(continente);
    }

}
