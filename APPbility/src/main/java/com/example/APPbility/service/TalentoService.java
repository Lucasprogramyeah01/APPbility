package com.example.APPbility.service;

import com.example.APPbility.dto.talento.EditTalentoCmd;
import com.example.APPbility.model.Talento;
import com.example.APPbility.repository.TalentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TalentoService {

    private final TalentoRepository talentoRepository;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Crear Talento.
    public Talento save(EditTalentoCmd nuevo){
        return talentoRepository.save(Talento.builder()
                .titulo(nuevo.titulo())
                .descripcion(nuevo.descripcion())
                .listaImagenes(nuevo.listaImagenes())
                .build());
    }

}
