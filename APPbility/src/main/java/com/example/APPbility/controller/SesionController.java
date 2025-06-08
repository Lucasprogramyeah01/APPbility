package com.example.APPbility.controller;

import com.example.APPbility.dto.bloque.GetBloqueDTOConUserID;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/sesion/")
@Tag(name = "Sesion", description = "Controlador de Sesion, para poder realizar sus operaciones de gestión.")
public class SesionController {

    private final SesionService sesionService;

    @GetMapping("{intercambioID}/listar")
    public List<GetSesionDTOConBloques> findSesionesFromIntercambio(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable Long intercambioID) {
        List<Sesion> listaSesiones = sesionService.findSesionesFromIntercambio(intercambioID, usuarioAutenticado);

        return listaSesiones.stream()
            .map(sesion -> GetSesionDTOConBloques.of(
                sesion,
                sesionService.findBloquesBySesionId(sesion.getId()).stream()
                    .map(bloque -> GetBloqueDTOConUserID.of(bloque, bloque.getUsuario()))
                    .toList())
            ).toList();
    }

    @DeleteMapping("/{sesionID}/eliminar")
    public ResponseEntity<?> eliminarSesion(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable Long sesionID) {
        sesionService.eliminarSesion(sesionID, usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }

}
