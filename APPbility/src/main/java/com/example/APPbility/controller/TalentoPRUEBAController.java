package com.example.APPbility.controller;

import com.example.APPbility.dto.talentoPRUEBA.EditTalentoCmd;
import com.example.APPbility.dto.talentoPRUEBA.GetTalentoDTO;
import com.example.APPbility.dto.talentoPRUEBA.GetTalentoDTOConUser;
import com.example.APPbility.service.TalentoService;
import com.example.APPbility.user.dto.GetUserDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/talento/")
@Tag(name = "Talento", description = "Controlador de Talento, para poder realizar sus operaciones de gestión.")
public class TalentoPRUEBAController {

    private final TalentoService talentoService;

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
    public GetTalentoDTO edit(/*@AuthenticationPrincipal User user,*/ @PathVariable Long id,
        @Valid @RequestPart("editTalentoCmd") EditTalentoCmd editTalentoCmd,
        @RequestPart("listaImagenes")MultipartFile... listaMultipartFile){
        return GetTalentoDTO.of(talentoService.edit(editTalentoCmd, id, listaMultipartFile));
    }

}
