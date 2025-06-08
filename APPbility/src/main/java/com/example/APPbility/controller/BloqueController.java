package com.example.APPbility.controller;

import com.example.APPbility.dto.bloque.CreateBloqueCMD;
import com.example.APPbility.dto.bloque.EditBloqueCMD;
import com.example.APPbility.dto.bloque.GetBloqueDTOConUserID;
import com.example.APPbility.model.Bloque;
import com.example.APPbility.service.BloqueService;
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
@RequestMapping("/bloque/")
@Tag(name = "Bloque", description = "Controlador de Bloque, para poder realizar sus operaciones de gestión.")
public class BloqueController {

    private final BloqueService bloqueService;

    @PostMapping("crear")
    public ResponseEntity<GetBloqueDTOConUserID> crearBloque(@AuthenticationPrincipal User usuarioAutenticado,
        @Valid @RequestBody CreateBloqueCMD nuevoBloque) {
        Bloque bloque = bloqueService.crearBloque(nuevoBloque, usuarioAutenticado);

        return ResponseEntity.status(HttpStatus.CREATED).body(GetBloqueDTOConUserID.of(bloque, usuarioAutenticado));
    }

    @PutMapping("/{bloqueID}/editar")
    public ResponseEntity<GetBloqueDTOConUserID> editarBloque(@AuthenticationPrincipal User usuarioAutenticado,
        @Valid @RequestBody EditBloqueCMD bloqueCMD, @PathVariable Long bloqueID) {
        Bloque bloqueEditado = bloqueService.editarBloque(bloqueID, bloqueCMD, usuarioAutenticado);

        return ResponseEntity.ok(GetBloqueDTOConUserID.of(bloqueEditado, usuarioAutenticado));
    }

    @DeleteMapping("/{bloqueID}/eliminar")
    public ResponseEntity<?> eliminarBloque(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable Long bloqueID) {
        bloqueService.eliminarBloque(bloqueID, usuarioAutenticado);

        return ResponseEntity.noContent().build();
    }

}
