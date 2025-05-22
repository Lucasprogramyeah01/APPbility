package com.example.APPbility.service;

import com.example.APPbility.error.entity.ContinenteNotFoundException;
import com.example.APPbility.error.entity.NivelNotFoundException;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Nivel;
import com.example.APPbility.repository.NivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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







}
