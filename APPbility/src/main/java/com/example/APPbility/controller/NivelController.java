package com.example.APPbility.controller;

import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.service.NivelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/nivel/")
@Tag(name = "Nivel", description = "Controlador de Nivel, para poder realizar sus operaciones de gestión.")
public class NivelController {

    private final NivelService nivelService;

    @GetMapping
    public Page<GetNivelDTO> findAll(@PageableDefault(sort = "orden", direction = Sort.Direction.ASC) Pageable pageable){
        return nivelService.findAll(pageable).map(GetNivelDTO::of);
    }

}
