package com.example.APPbility.controller;

import com.example.APPbility.dto.intercambio.*;
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

        return ResponseEntity.status(HttpStatus.CREATED).body(
            GetIntercambioDTOParaProponer.of(
                intercambioPropuesto,
                GetUserDTO.of(usuarioDemandante),
                GetUserDTO.of(intercambioPropuesto.getUsuarioSolicitado()),
                GetTalentoDTOConNivel.of(
                    intercambioPropuesto.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioPropuesto.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(
                    intercambioPropuesto.getTalentoSugerido(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioPropuesto.getTalentoSugerido().getId()))
                )
            )
        );
    }

    @PutMapping("/aceptar/{id}")
    public ResponseEntity<GetIntercambioDTOAlAceptar> aceptarIntercambio(@AuthenticationPrincipal User usuarioSolicitado,
        @Valid @RequestBody AceptarIntercambioCMD intercambioCMD, @PathVariable Long id) {
        Intercambio intercambioAceptado = intercambioService.aceptarIntercambio(id, intercambioCMD, usuarioSolicitado);

        return ResponseEntity.ok(
            GetIntercambioDTOAlAceptar.of(
                intercambioAceptado,
                GetUserDTO.of(intercambioAceptado.getUsuarioDemandante()),
                GetUserDTO.of(usuarioSolicitado),
                GetTalentoDTOConNivel.of(
                    intercambioAceptado.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioAceptado.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(
                    intercambioAceptado.getTalentoAceptado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioAceptado.getTalentoAceptado().getId()))
                )
            )
        );
    }

    @PutMapping("/rechazar/{id}")
    public ResponseEntity<GetIntercambioDTOAlRechazar> rechazarIntercambio(@AuthenticationPrincipal User usuarioSolicitado,
        @PathVariable Long id) {
        Intercambio intercambioRechazado = intercambioService.rechazarIntercambio(id, usuarioSolicitado);

        return ResponseEntity.ok(
            GetIntercambioDTOAlRechazar.of(
                intercambioRechazado,
                GetUserDTO.of(intercambioRechazado.getUsuarioDemandante()),
                GetUserDTO.of(usuarioSolicitado),
                GetTalentoDTOConNivel.of(
                    intercambioRechazado.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioRechazado.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(
                    intercambioRechazado.getTalentoSugerido(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioRechazado.getTalentoSugerido().getId()))
                )
            )
        );
    }





}
