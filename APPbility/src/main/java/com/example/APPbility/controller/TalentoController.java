package com.example.APPbility.controller;

import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.dto.talento.CreateTalentoCMD;
import com.example.APPbility.dto.talento.GetTalentoDTOCompleto;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Talento;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/talento/")
@Tag(name = "Talento", description = "Controlador de Talento, para poder realizar sus operaciones de gestión.")
public class TalentoController {

    private final TalentoService talentoService;

    @Operation(summary = "Devuelve una lista paginada de todos los Talentos de un Usuario.",
            description = "Devuelve una lista paginada de todos Talentos asociados a un Usuario específico, ordenados " +
            "por su atributo 'titulo'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de Talentos obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Page.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "content": [
                                    {
                                         "id": 1,
                                         "titulo": "Karate",
                                         "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional
                                            desde hace años y dispongo de cinturón negro, además también enseño trucos
                                            de defensa personal contra armas de fuego y llaves de otras artes 
                                            marciales.",
                                         "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                         "nivel": {
                                             "id": 5,
                                             "nombre": "Experto",
                                             "color": "#e80044",
                                             "orden": 5
                                         }
                                     },
                                     {
                                         "id": 2,
                                         "titulo": "Inglés para conversar",
                                         "descripcion": "He vivido 3 años en Reino Unido y puedo ayudar a mejorar la 
                                            fluidez, pronunciación y vocabulario en inglés.",
                                         "imagen": null,
                                         "nivel": {
                                             "id": 3,
                                             "nombre": "Intermedio",
                                             "color": "#6dd702",
                                             "orden": 3
                                         }
                                     }
                                ],
                                "pageable": {
                                    "sort": {
                                        "sorted": true,
                                        "unsorted": false,
                                        "empty": false
                                    },
                                    "pageNumber": 0,
                                    "pageSize": 10,
                                    "offset": 0,
                                    "unpaged": false,
                                    "paged": true
                                },
                                "totalElements": 2,
                                "totalPages": 1,
                                "last": true,
                                "size": 10,
                                "number": 0,
                                "sort": {
                                    "sorted": true,
                                    "unsorted": false,
                                    "empty": false
                                },
                                "numberOfElements": 2,
                                "first": true,
                                "empty": false
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "404",
                description = "Usuario (del que se intenta obtener la lista de talentos) no encontrado.",
                content = @Content)
    })
    @GetMapping("{id}")
    public Page<GetTalentoDTOConNivel> findTalentosfromUsuario(@PageableDefault(sort = "titulo",
        direction = Sort.Direction.ASC) Pageable pageable, @PathVariable UUID id) {
        Page<Talento> listaTalentos = talentoService.findTalentosfromUsuario(id, pageable);

        List<GetTalentoDTOConNivel> listaTalentosConNivel = listaTalentos.stream()
            .map(t -> GetTalentoDTOConNivel.of(t, GetNivelDTO.of(talentoService.getNivelByTalentoID(t.getId()))))
            .toList();

        return new PageImpl<>(listaTalentosConNivel, pageable, listaTalentos.getTotalElements());
    }

    @Operation(summary = "Crea un nuevo Talento.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha creado el Talento correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetTalentoDTOCompleto.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "titulo": "Cocina vegetariana casera",
                                "descripcion": "Aprende a preparar recetas vegetarianas sencillas y sabrosas con
                                    ingredientes naturales. Ideal para quienes quieren mejorar su alimentación sin 
                                    complicaciones.",
                                "nivelID": 3
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "415",
            description = "Tipo de imagen no soportado.",
            content = @Content)
    })
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GetTalentoDTOCompleto> save(@AuthenticationPrincipal User user, @Valid
        @RequestPart("talento") CreateTalentoCMD nuevo, @RequestPart(value = "imagen", required = false)
        MultipartFile multipartFile){
        Talento nuevoTalento = talentoService.save(nuevo, multipartFile, user);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(GetTalentoDTOCompleto.of(nuevoTalento,
                GetNivelDTO.of(talentoService.getNivelByTalentoID(nuevoTalento.getId())), GetUserDTO.of(user)));
    }

    @Operation(summary = "Edita un Talento ya existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Se ha editado el Talento correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetTalentoDTOCompleto.class),
                examples = {@ExampleObject(
                    value = """
                            {
                              "titulo": "Cocina cárnica gourmet",
                              "descripcion": "Domina técnicas avanzadas para preparar cortes premium de carne,
                                marinados intensos y métodos de cocción como sous-vide y braseado. Perfecto para
                                sorprender en cenas especiales o llevar tus habilidades culinarias al siguiente
                                nivel.",
                              "nivelID": 4
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para editar este Talento.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Talento no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "415",
            description = "Tipo de imagen no soportado.",
            content = @Content)
    })
    @PutMapping(value = "{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public GetTalentoDTOCompleto edit(@AuthenticationPrincipal User user, @Valid @RequestPart("talento") CreateTalentoCMD editTalentoCMD,
        @RequestPart(value = "imagen", required = false) MultipartFile multipartFile, @PathVariable Long id){
        Talento talentoEditado = talentoService.edit(editTalentoCMD, multipartFile, user, id);

        return GetTalentoDTOCompleto.of(talentoEditado,
            GetNivelDTO.of(talentoService.getNivelByTalentoID(talentoEditado.getId())), GetUserDTO.of(user));
    }

    @Operation(summary = "Elimina un talento existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Se ha eliminado el Talento correctamente.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para eliminar este Talento.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Talento no encontrado.",
            content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable Long id){
        talentoService.delete(id, user);

        return ResponseEntity.noContent().build();
    }

}