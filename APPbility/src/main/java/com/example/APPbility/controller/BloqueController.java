package com.example.APPbility.controller;

import com.example.APPbility.dto.bloque.CreateBloqueCMD;
import com.example.APPbility.dto.bloque.EditBloqueCMD;
import com.example.APPbility.dto.bloque.GetBloqueDTOConUserID;
import com.example.APPbility.model.Bloque;
import com.example.APPbility.service.BloqueService;
import com.example.APPbility.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crea un nuevo Bloque.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha creado el Bloque correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetBloqueDTOConUserID.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "titulo": "Inglés - Past Tenses",
                                "descripcion": "Repasaremos los tiempos en pasado de inglés y realizaremos tanto una entrevista como un examen escrito para poner a prueba lo aprendido.",
                                "hora": "22:30",
                                "fechaSesion": "2025-06-25",
                                "intercambioID": "4"
                            }
                        """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content)
    })
    @PostMapping("crear")
    public ResponseEntity<GetBloqueDTOConUserID> crearBloque(@AuthenticationPrincipal User usuarioAutenticado,
        @Valid @RequestBody CreateBloqueCMD nuevoBloque) {
        Bloque bloque = bloqueService.crearBloque(nuevoBloque, usuarioAutenticado);

        return ResponseEntity.status(HttpStatus.CREATED).body(GetBloqueDTOConUserID.of(bloque, usuarioAutenticado));
    }

    @Operation(summary = "Edita un Bloque ya existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Se ha editado el Bloque correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetBloqueDTOConUserID.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "titulo": "Alemán - Futur I",
                                "descripcion": "Repasaremos los tiempos en futuro de alemán y realizaremos tanto una entrevista como un examen escrito para poner a prueba lo aprendido.",
                                "hora": "13:30"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado o no autorizado.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Bloque no encontrado.",
            content = @Content)
    })
    @PutMapping("/{bloqueID}/editar")
    public ResponseEntity<GetBloqueDTOConUserID> editarBloque(@AuthenticationPrincipal User usuarioAutenticado,
        @Valid @RequestBody EditBloqueCMD bloqueCMD, @PathVariable Long bloqueID) {
        Bloque bloqueEditado = bloqueService.editarBloque(bloqueID, bloqueCMD, usuarioAutenticado);

        return ResponseEntity.ok(GetBloqueDTOConUserID.of(bloqueEditado, usuarioAutenticado));
    }

    @Operation(summary = "Elimina un Bloque existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Se ha eliminado el Bloque correctamente.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Bloque no encontrado.",
            content = @Content)
    })
    @DeleteMapping("/{bloqueID}/eliminar")
    public ResponseEntity<?> eliminarBloque(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable Long bloqueID) {
        bloqueService.eliminarBloque(bloqueID, usuarioAutenticado);

        return ResponseEntity.noContent().build();
    }

}
