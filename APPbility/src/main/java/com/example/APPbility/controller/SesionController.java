package com.example.APPbility.controller;

import com.example.APPbility.dto.bloque.GetBloqueDTOConUserID;
import com.example.APPbility.dto.sesion.GetSesionDTOConBloques;
import com.example.APPbility.model.Sesion;
import com.example.APPbility.service.SesionService;
import com.example.APPbility.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Devuelve una lista con todas las Sesiones de un Intercambio.",
        description = "Devuelve una lista con todas las Sesiones asociadas a un Intercambio específico, incluyendo " +
            "sus Bloques.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de sesiones obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = GetSesionDTOConBloques.class)),
                examples = {@ExampleObject(
                    value = """
                            [
                                {
                                    "id": 1,
                                    "fecha": "2025-06-07",
                                    "listaBloques": [
                                        {
                                            "id": 1,
                                            "titulo": "Cepillado",
                                            "descripcion": "Hoy le enseñaré a cepillar el pelo de un perro regularmente
                                                para mantenerlo limpio y prevenir problemas de piel.",
                                            "hora": "12:30:00",
                                            "usuarioID": "d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007",
                                            "username": "MalenG"
                                        },
                                        {
                                            "id": 2,
                                            "titulo": "Estudio de células y tejidos",
                                            "descripcion": "Toca preparar muestras de células humanas, vegetales o
                                                animales para observar su estructura y funcionamiento.",
                                            "hora": "18:00:00",
                                            "usuarioID": "9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00",
                                            "username": "Arman"
                                        }
                                    ]
                                },
                                {
                                    "id": 2,
                                    "fecha": "2025-06-25",
                                    "listaBloques": [
                                        {
                                            "id": 3,
                                            "titulo": "Identificación de microorganismos",
                                            "descripcion": "Utilizaremos los microscopios para
                                                identificar bacterias, virus y otros microorganismos
                                                en muestras de agua, suelo o aire.",
                                            "hora": "20:10:00",
                                            "usuarioID": "9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00",
                                            "username": "Arman"
                                        }
                                    ]
                                }
                            ]
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para acceder a las Sesiones este Intercambio.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio no encontrado.",
            content = @Content)
    })
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

}
