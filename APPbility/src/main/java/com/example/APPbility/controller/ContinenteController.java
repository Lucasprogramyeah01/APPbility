package com.example.APPbility.controller;

import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.dto.continente.GetContinenteDTOCompleto;
import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.dto.pais.GetPaisDTOCompleto;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import com.example.APPbility.service.ContinenteService;
import com.example.APPbility.user.dto.GetUserDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Continente", description = "Controlador de Continente, para poder realizar sus operaciones de gestión.")
public class ContinenteController {

    private final ContinenteService continenteService;

    @GetMapping("/continente/")
    public Page<GetContinenteDTO> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return continenteService.findAll(pageable).map(GetContinenteDTO::of);
    }

    @GetMapping("/continente/{id}")
    public GetContinenteDTOCompleto findByID(@PathVariable Long id){
        List<GetPaisDTO> listaPaises = continenteService.getListaPaisesByContinenteID(id).stream().map(GetPaisDTO::of).toList();

        Continente c = continenteService.findById(id);

        return GetContinenteDTOCompleto.of(c, listaPaises);
    }

}
