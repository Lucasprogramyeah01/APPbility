package com.example.APPbility.controller;

import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.dto.pais.*;
import com.example.APPbility.model.Pais;
import com.example.APPbility.service.PaisService;
import com.example.APPbility.user.dto.GetUserSinPaisNativoDTO;
import com.example.APPbility.user.dto.GetUserSinPaisResidenciaDTO;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/pais/")
@Tag(name = "Pais", description = "Controlador de Pais, para poder realizar sus operaciones de gestión.")
public class PaisController {

    private final PaisService paisService;

    @Operation(summary = "Devuelve una lista paginada de todos los Países.",
        description = "Devuelve una lista paginada de todos los niveles ordenados alfabéticamente por su " +
            "atributo 'nombre'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de Países obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = GetPaisDTO.class)),
                examples = {@ExampleObject(
                    value = """
                            {
                                "content": [
                                    {
                                        "id": 5,
                                        "nombre": "España",
                                        "codigoISO": "ES",
                                        "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/es.png"
                                    },
                                    {
                                        "id": 10,
                                        "nombre": "Argentina",
                                        "codigoISO": "AR",
                                        "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ar.png"
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
            description = "No hay países registrados.",
            content = @Content)
    })
    @GetMapping
    public Page<GetPaisDTO> findAll(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable){
        return paisService.findAll(pageable).map(GetPaisDTO::of);
    }

    @Operation(summary = "Devuelve un País buscado por ID.",
        description = "Devuelve un país incluyendo su continente y usuarios relacionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "País encontrado.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetPaisDTOCompleto.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                 "id": 5,
                                 "nombre": "Albania",
                                 "codigoISO": "AL",
                                 "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/al.png",
                                 "continente": {
                                     "id": 3,
                                     "nombre": "Europa"
                                 },
                                 "listaUsuariosNativos": [
                                     {
                                         "id": "f13a2e98-70f5-4d61-93ab-349be7022025",
                                         "username": "Elirart",
                                         "password": "{noop}elira123",
                                         "email": "elira.qose@gmail.com",
                                         "nombre": "Elira",
                                         "apellidos": "Qose",
                                         "fechaNacimiento": "1995-08-17",
                                         "sexo": "MUJER",
                                         "modalidadPreferida": "VIRTUAL",
                                         "numTelefono": "+355681234567",
                                         "mostrarNumTelefono": true,
                                         "color": "#FF00CC",
                                         "imagenPerfil": "https://medias.artmajeur.com/mini/17840476_1.jpg",
                                         "idiomaNativo": "sq",
                                         "listaOtrosIdiomas": [
                                             "en",
                                             "it"
                                         ],
                                         "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 aÃ±os de experiencia en talleres comunitarios y proyectos escolares.",
                                         "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores.",
                                         "listaEnlacesExternos": [
                                             "https://www.instagram.com/eliraart/",
                                             "https://www.behance.net/eliraqose"
                                         ],
                                         "paisResidencia": {
                                             "id": 4,
                                             "nombre": "Antigua y Barbuda",
                                             "codigoISO": "AG",
                                             "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ag.png"
                                         }
                                     }
                                 ],
                                 "listaUsuariosResidentes": []
                             }
                            """
                )}
            )}),
            @ApiResponse(responseCode = "404",
                description = "País no encontrado.",
                content = @Content)
    })
    @GetMapping("{id}")
    public GetPaisDTOCompleto findByID(@PathVariable Long id){
        Pais p = paisService.findById(id);

        GetContinenteDTO getContinenteDTO = GetContinenteDTO.of(paisService.getContinenteByPaisID(id));
        List<GetUserSinPaisNativoDTO> listaUsuariosNativos = paisService.getListaUsuariosNativosByPaisID(id).stream()
            .map(user -> GetUserSinPaisNativoDTO.of(user, GetPaisDTO.of(user.getPaisResidencia()))).toList();
        List<GetUserSinPaisResidenciaDTO> listaUsuariosResidentes = paisService.getListaUsuariosResidentesByPaisID(id).stream()
            .map(user -> GetUserSinPaisResidenciaDTO.of(user, GetPaisDTO.of(user.getPaisNativo()))).toList();

        return GetPaisDTOCompleto.of(p, getContinenteDTO, listaUsuariosNativos, listaUsuariosResidentes);
    }

    @Operation(summary = "Crea un nuevo País.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha creado el País correctamente.",
            content = { @Content(mediaType = "multipart/form-data",
                schema = @Schema(implementation = GetPaisDTOConContinente.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "nombre": "Moldavia",
                                "codigoISO": "md",
                                "continenteID": 3
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "415",
            description = "Tipo de archivo no soportado.",
            content = @Content),
    })
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GetPaisDTOConContinente> save(@Valid @RequestPart("pais") CreatePaisCMD nuevo,
        @RequestPart(value = "bandera", required = true) MultipartFile multipartFile){
        Pais nuevoPais = paisService.save(nuevo, multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(GetPaisDTOConContinente.of(nuevoPais, GetContinenteDTO.of(paisService.getContinenteByPaisID(nuevoPais.getId()))));
    }

    @Operation(summary = "Edita un País ya existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Se ha editado el País correctamente.",
            content = { @Content(mediaType = "multipart/form-data",
                schema = @Schema(implementation = GetPaisDTOConContinente.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "nombre": "Turkmenistán",
                                "codigoISO": "tm",
                                "continenteID": 5
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "País no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "415",
            description = "Tipo de archivo no soportado.",
            content = @Content),
    })
    @PutMapping(value = "{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public GetPaisDTOConContinente edit(@Valid @RequestPart("pais") EditPaisCMD editPaisCMD,
        @RequestPart(value = "bandera", required = true) MultipartFile multipartFile, @PathVariable Long id){
        Pais paisEditado = paisService.edit(editPaisCMD, multipartFile, id);

        return GetPaisDTOConContinente.of(paisEditado, GetContinenteDTO.of(paisService.getContinenteByPaisID(id)));
    }

    @Operation(summary = "Elimina un país existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Se ha eliminado el País correctamente.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "País no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "No se puede eliminar porque tiene Usuarios asociados.",
            content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        paisService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
