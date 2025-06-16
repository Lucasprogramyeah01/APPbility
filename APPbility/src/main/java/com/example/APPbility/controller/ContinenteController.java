package com.example.APPbility.controller;

import com.example.APPbility.dto.continente.CreateContinenteCMD;
import com.example.APPbility.dto.continente.EditContinenteCMD;
import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.dto.continente.GetContinenteDTOCompleto;
import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.model.Continente;
import com.example.APPbility.service.ContinenteService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/continente/")
@Tag(name = "Continente", description = "Controlador de Continente, para poder realizar sus operaciones de gestión.")
public class ContinenteController {

    private final ContinenteService continenteService;

    @Operation(summary = "Devuelve una lista paginada de todos los Continentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de Continentes obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = GetContinenteDTO.class)),
                examples = {@ExampleObject(
                    value = """
                            {
                                "content": [
                                    {
                                        "id": 1,
                                        "nombre": "América del Norte"
                                    },
                                    {
                                        "id": 2,
                                        "nombre": "España"
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
            description = "No hay Continentes registrados.",
            content = @Content)
    })
    @GetMapping
    public Page<GetContinenteDTO> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return continenteService.findAll(pageable).map(GetContinenteDTO::of);
    }

    @Operation(summary = "Devuelve un Continente buscado por ID.",
        description = "Devuelve un Continente con la lista de sus Países.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Continente encontrado.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetContinenteDTOCompleto.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": 3,
                                "nombre": "Europa",
                                "listaPaises": [
                                    {
                                        "id": 1,
                                        "nombre": "Andorra",
                                        "codigoISO": "AD",
                                        "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                    },
                                    {
                                        "id": 5,
                                        "nombre": "Albania",
                                        "codigoISO": "AL",
                                        "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/al.png"
                                    },
                                    {
                                        "id": 10,
                                        "nombre": "España",
                                        "codigoISO": "ES",
                                        "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/es.png"
                                    }
                                ]
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "404",
            description = "Continente no encontrado.",
            content = @Content)
    })
    @GetMapping("{id}")
    public GetContinenteDTOCompleto findByID(@PathVariable Long id){
        List<GetPaisDTO> listaPaises = continenteService.getListaPaisesByContinenteID(id).stream().map(GetPaisDTO::of).toList();

        Continente c = continenteService.findById(id);

        return GetContinenteDTOCompleto.of(c, listaPaises);
    }

    @Operation(summary = "Crea un nuevo Continente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha creado el Continente correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Continente.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "nombre": "Antártida"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<Continente> save(@Valid @RequestBody CreateContinenteCMD nuevo){
        return ResponseEntity.status(HttpStatus.CREATED).body(continenteService.save(nuevo));
    }

    @Operation(summary = "Edita un Continente ya existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Se ha editado el Continente correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetContinenteDTO.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "nombre": "Oceanía"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Continente no encontrado.",
            content = @Content)
    })
    @PutMapping("{id}")
    public GetContinenteDTO edit(@Valid @RequestBody EditContinenteCMD editContinenteCMD, @PathVariable Long id){
        return GetContinenteDTO.of(continenteService.edit(editContinenteCMD, id));
    }

    @Operation(summary = "Elimina un Continente existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Se ha eliminado el Continente correctamente.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Continente no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "No se puede eliminar porque tiene Países asociados.",
            content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        continenteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
