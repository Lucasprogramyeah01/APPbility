package com.example.APPbility.controller;

import com.example.APPbility.dto.tag.EditTagCmd;
import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.dto.tag.GetTagDTOCompleto;
import com.example.APPbility.service.TagService;
import com.example.APPbility.user.dto.GetUserDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag/")
@Tag(name = "Tag", description = "Controlador de Tag, para poder realizar sus operaciones de gestión.")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Obtiene una lista de todas los Tags.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado Tags.",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = com.example.APPbility.model.Tag.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 10,
                                                    "nombre": "Aplicado/a"
                                                },
                                                {
                                                    "id": 5,
                                                    "nombre": "Autodidacta"
                                                },
                                                {
                                                    "id": 11,
                                                    "nombre": "Con ganas"
                                                }
                                            ]                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado Tags.",
                    content = @Content),
    })
    @GetMapping
    public Page<GetTagDTO> findAll(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable){
        return tagService.findAll(pageable);
    }

    @Operation(summary = "Obtiene el Tag buscado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el Tag.",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = com.example.APPbility.model.Tag.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "nombre": "Empático/a",
                                                "listaUsuarios": [
                                                    {
                                                        "id": "550e8400-e29b-41d4-a716-446655440000",
                                                        "username": "SkyHunter92",
                                                        "email": "juan.perez@gmail.com",
                                                        "nombre": "Juan",
                                                        "apellidos": "Pérez",
                                                        "sexo": "HOMBRE",
                                                        "numTelefono": "123456789",
                                                        "imagenPerfil": null,
                                                        "fechaNacimiento": "1990-01-01",
                                                        "lugarNacimiento": "MADRID",
                                                        "lugarResidencia": "BARCELONA",
                                                        "puntosPopularidad": 100,
                                                        "idiomaNativo": "Español",
                                                        "otrosIdiomas": "Inglés",
                                                        "conocimientos": "Programación",
                                                        "descripcion": "Apasionado de la tecnología y los videojuegos."
                                                    }
                                                ]
                                            }                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún Tag con ese ID.",
                    content = @Content),
    })
    @GetMapping("{id}")
    public GetTagDTOCompleto findByID(@PathVariable Long id){
        Set<GetUserDTO> listaUsuarios = tagService.getListaUsuariosByTagID(id);

        com.example.APPbility.model.Tag t = tagService.findById(id);

        return GetTagDTOCompleto.of(t, listaUsuarios);
    }

    @Operation(summary = "Crea un nuevo Tag.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el Tag correctamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EditTagCmd.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "nombre": "Vagoneta"
                                            }                                
                                        """
                            )}
                    )}),
    })
    @PostMapping
    public ResponseEntity<com.example.APPbility.model.Tag> save(@Valid @RequestBody EditTagCmd nuevo){
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.save(nuevo));
    }

    @Operation(summary = "Edita un Tag ya creado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado el Tag correctamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EditTagCmd.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "nombre": "Revoltoso/a"
                                            }                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún Tag.",
                    content = @Content),
    })
    @PutMapping("{id}")
    public GetTagDTO edit(@RequestBody EditTagCmd editTagCmd, @PathVariable Long id){
        return GetTagDTO.of(tagService.edit(editTagCmd, id));
    }

    

}
