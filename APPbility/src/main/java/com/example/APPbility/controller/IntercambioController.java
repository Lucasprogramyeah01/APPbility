package com.example.APPbility.controller;

import com.example.APPbility.dto.intercambio.CreateIntercambioCMD;
import com.example.APPbility.dto.intercambio.GetIntercambioDTOParaProponer;
import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.service.IntercambioService;
import com.example.APPbility.service.TalentoService;
import com.example.APPbility.user.dto.GetUserDTO;
import com.example.APPbility.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/intercambio/")
@Tag(name = "Intercambio", description = "Controlador de Intercambio, para poder realizar sus operaciones de gestión.")
public class IntercambioController {

    private final IntercambioService intercambioService;
    private final TalentoService talentoService;

    @PostMapping("/proponer")
    public ResponseEntity<GetIntercambioDTOParaProponer> proponerIntercambio(@AuthenticationPrincipal User usuarioDemandante,
        @Valid @RequestBody CreateIntercambioCMD nuevo) {
        Intercambio intercambioPropuesto = intercambioService.proponerIntercambio(nuevo, usuarioDemandante);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(GetIntercambioDTOParaProponer
                .of(intercambioPropuesto, GetUserDTO.of(usuarioDemandante),
                    GetUserDTO.of(intercambioPropuesto.getUsuarioSolicitado()),
                    GetTalentoDTOConNivel.of(intercambioPropuesto.getTalentoSolicitado(), GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioPropuesto.getTalentoSolicitado().getId()))),
                    GetTalentoDTOConNivel.of(intercambioPropuesto.getTalentoSugerido(), GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioPropuesto.getTalentoSugerido().getId())))
                )
            );
    }



}
