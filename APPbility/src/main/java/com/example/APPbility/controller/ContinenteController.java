package com.example.APPbility.controller;

import com.example.APPbility.dto.continente.CreateContinenteCMD;
import com.example.APPbility.dto.continente.EditContinenteCMD;
import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.dto.continente.GetContinenteDTOCompleto;
import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.model.Continente;
import com.example.APPbility.service.ContinenteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/continente/")
@Tag(name = "Continente", description = "Controlador de Continente, para poder realizar sus operaciones de gestión.")
public class ContinenteController {

    private final ContinenteService continenteService;

    @GetMapping
    public Page<GetContinenteDTO> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return continenteService.findAll(pageable).map(GetContinenteDTO::of);
    }

    @GetMapping("{id}")
    public GetContinenteDTOCompleto findByID(@PathVariable Long id){
        List<GetPaisDTO> listaPaises = continenteService.getListaPaisesByContinenteID(id).stream().map(GetPaisDTO::of).toList();

        Continente c = continenteService.findById(id);

        return GetContinenteDTOCompleto.of(c, listaPaises);
    }

    @PostMapping
    public ResponseEntity<Continente> save(@Valid @RequestBody CreateContinenteCMD nuevo){
        return ResponseEntity.status(HttpStatus.CREATED).body(continenteService.save(nuevo));
    }

    @PutMapping("{id}")
    public GetContinenteDTO edit(@Valid @RequestBody EditContinenteCMD editContinenteCMD, @PathVariable Long id){
        return GetContinenteDTO.of(continenteService.edit(editContinenteCMD, id));
    }

}
