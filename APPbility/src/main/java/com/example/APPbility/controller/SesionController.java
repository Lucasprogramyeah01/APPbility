package com.example.APPbility.controller;

import com.example.APPbility.dto.sesion.CreateSesionCMD;
import com.example.APPbility.dto.sesion.GetSesionDTO;
import com.example.APPbility.dto.sesion.GetSesionDTOConBloques;
import com.example.APPbility.model.Sesion;
import com.example.APPbility.service.SesionService;
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
@RequestMapping("/sesion/")
@Tag(name = "Sesion", description = "Controlador de Sesion, para poder realizar sus operaciones de gestión.")
public class SesionController {

    private final SesionService sesionService;

    @PostMapping("/{IntercambioID}/crear")
    public ResponseEntity<GetSesionDTO> crearSesion(@AuthenticationPrincipal User usuarioAutenticado,
        @Valid @RequestBody CreateSesionCMD nuevo, @PathVariable Long IntercambioID) {
        Sesion nuevaSesion = sesionService.crearSesion(nuevo, IntercambioID, usuarioAutenticado);

        return ResponseEntity.status(HttpStatus.CREATED).body(GetSesionDTO.of(nuevaSesion));
    }



}
