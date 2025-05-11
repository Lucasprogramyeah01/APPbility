package com.example.APPbility.service;

import com.example.APPbility.error.TagPRUEBANotFoundException;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import com.example.APPbility.repository.ContinenteRepository;
import com.example.APPbility.user.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            throw new UserNotFoundException();  //CREAR EXCEPCIÓN PARA CONTINENTE.
        return result;
    }

    //Buscar Continente por ID.
    public Continente findById(Long id){
        Optional<Continente> continenteOptional = continenteRepository.findById(id);

        if(continenteOptional.isPresent())
            return continenteOptional.get();
        throw new TagPRUEBANotFoundException(id); //CREAR EXCEPCIÓN PARA CONTINENTE.
    }

}
