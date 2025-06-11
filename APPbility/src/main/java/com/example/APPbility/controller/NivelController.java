package com.example.APPbility.controller;

import com.example.APPbility.dto.nivel.CreateNivelCMD;
import com.example.APPbility.dto.nivel.EditNivelCMD;
import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.model.Nivel;
import com.example.APPbility.service.NivelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/nivel/")
@Tag(name = "Nivel", description = "Controlador de Nivel, para poder realizar sus operaciones de gestión.")
public class NivelController {

    private final NivelService nivelService;

    @Operation(summary = "Devuelve uan lista paginada de todos los Niveles.",
        description = "Devuelve una lista paginada de todos los niveles ordenados por su atributo 'orden'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de Niveles obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = GetNivelDTO.class)),
                examples = {@ExampleObject(
                    value = """
                            {
                                "content": [
                                    {
                                        "id": 1,
                                        "nombre": "Inicial",
                                        "color": "#b7b7b7",
                                        "orden": 1
                                    },
                                    {
                                        "id": 2,
                                        "nombre": "Básico",
                                        "color": "#009deb",
                                        "orden": 2
                                    },
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
                                "totalPages": 1,
                                "totalElements": 2,
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
        @ApiResponse(responseCode = "204",
                description = "No hay Niveles registrados.",
                content = @Content)
    })
    @GetMapping
    public Page<GetNivelDTO> findAll(@PageableDefault(sort = "orden", direction = Sort.Direction.ASC) Pageable pageable){
        return nivelService.findAll(pageable).map(GetNivelDTO::of);
    }

    @Operation(summary = "Crea un nuevo Nivel.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha creado el Nivel correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Nivel.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "nombre": "Moderado",
                                "color": "#decb00",
                                "orden": 3
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<Nivel> save(@Valid @RequestBody CreateNivelCMD nuevo){
        return ResponseEntity.status(HttpStatus.CREATED).body(nivelService.save(nuevo));
    }

    @Operation(summary = "Edita un Nivel ya existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Se ha editado el Nivel correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetNivelDTO.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "nombre": "Principiante",
                                "color": "#ff69c1",
                                "orden": 1
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Nivel no encontrado.",
            content = @Content)
    })
    @PutMapping("{id}")
    public GetNivelDTO edit(@Valid @RequestBody EditNivelCMD editNivelCMD, @PathVariable Long id){
        return GetNivelDTO.of(nivelService.edit(editNivelCMD, id));
    }

    @Operation(summary = "Elimina un Nivel existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Se ha eliminado el Nivel correctamente.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Nivel no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "No se puede eliminar porque tiene Talentos asociados.",
            content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        nivelService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
