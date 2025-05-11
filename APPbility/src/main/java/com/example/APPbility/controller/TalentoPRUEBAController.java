package com.example.APPbility.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/talento/")
@Tag(name = "Talento", description = "Controlador de Talento, para poder realizar sus operaciones de gestión.")
public class TalentoPRUEBAController {

    /*private final TalentoService talentoService;

    @Operation(summary = "Crea un nuevo Talento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el Talento correctamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EditTalentoCmd.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "titulo": "Marketing",
                                                "descripcion": "Hice un grado superior de Marketing el año pasado. :)",
                                                "listaImagenes": []
                                            }                                
                                        """
                            )}
                    )}),
    })
    @PostMapping
    public ResponseEntity<GetTalentoDTOConUser> save(@AuthenticationPrincipal User user,
        @Valid @RequestPart("editTalentoCmd") EditTalentoCmd nuevo,
        @RequestPart("listaImagenes")MultipartFile... listaMultipartFile){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(GetTalentoDTOConUser.of(talentoService.save(user, nuevo, listaMultipartFile), GetUserDTO.of(user)));
    }

    //ESTE MÉTOD0 FUNCIONA RARO.
    @PutMapping("{id}")
    @PreAuthorize("@talentoService.existsTalentoByUsuario_Id(authentication.principal.id)")
    public GetTalentoDTO edit(@AuthenticationPrincipal User user, @PathVariable Long id,
        @Valid @RequestPart("editTalentoCmd") EditTalentoCmd editTalentoCmd,
        @RequestPart("listaImagenes")MultipartFile... listaMultipartFile){
        return GetTalentoDTO.of(talentoService.edit(editTalentoCmd, id, listaMultipartFile));
    }*/

}
