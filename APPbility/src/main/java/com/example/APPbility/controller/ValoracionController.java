package com.example.APPbility.controller;

import com.example.APPbility.dto.intercambio.GetIntercambioDTOSinUsers;
import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.dto.valoracion.CreateValoracionCMD;
import com.example.APPbility.dto.valoracion.GetValoracionDTOConUsersEIntercambio;
import com.example.APPbility.model.Valoracion;
import com.example.APPbility.service.TalentoService;
import com.example.APPbility.service.ValoracionService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/valoracion/")
@Tag(name = "Valoracion", description = "Controlador de Valoracion, para poder realizar sus operaciones de gestión.")
public class ValoracionController {

    private final ValoracionService valoracionService;
    private final TalentoService talentoService;

    @Operation(summary = "Crea una nueva Valoración.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha creado la Valoración correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetValoracionDTOConUsersEIntercambio.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "puntuacion": 10,
                                "titulo": "Muy buena persona",
                                "resenha": "Ha sido un intercambio muy agradable y es muy buena persona. :)"
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
            description = "Usuario no autorizado para realizar una Valoración en este Intercambio.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "El Usuario ya ha valorado este Intercambio.",
            content = @Content)
    })
    @PostMapping("/{intercambioID}/crear")
    public ResponseEntity<GetValoracionDTOConUsersEIntercambio> crearValoracion(@AuthenticationPrincipal User usuarioEscritor,
        @Valid @RequestBody CreateValoracionCMD valoracionCMD, @PathVariable Long intercambioID) {
        Valoracion valoracion = valoracionService.crearValoracion(intercambioID, valoracionCMD, usuarioEscritor);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            GetValoracionDTOConUsersEIntercambio.of(
                valoracion,
                GetUserDTO.of(usuarioEscritor),
                GetUserDTO.of(valoracion.getUsuarioValorado()),
                GetIntercambioDTOSinUsers.of(
                    valoracion.getIntercambio(),
                    GetTalentoDTOConNivel.of(valoracion.getIntercambio().getTalentoSolicitado(),
                        GetNivelDTO.of(
                            talentoService.getNivelByTalentoID(valoracion.getIntercambio().getTalentoSolicitado().getId())
                        )
                    ),
                    valoracion.getIntercambio().getTalentoAceptado() != null ?
                        GetTalentoDTOConNivel.of(valoracion.getIntercambio().getTalentoAceptado(),
                            GetNivelDTO.of(
                                talentoService.getNivelByTalentoID(valoracion.getIntercambio().getTalentoAceptado().getId())
                            )
                        ) : null,
                    GetTalentoDTOConNivel.of(valoracion.getIntercambio().getTalentoSugerido(),
                        GetNivelDTO.of(
                            talentoService.getNivelByTalentoID(valoracion.getIntercambio().getTalentoSugerido().getId())
                        )
                    )
                )
            )
        );
    }

    @Operation(summary = "Devuelve una lista paginada de todas las Valoraciones de un Usuario.",
        description = "Devuelve una lista paginada de todas las Valoraciones relacionadas con un Usuario (como " +
        "   escritor o valorado).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de valoraciones obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Page.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "content": [
                                    {
                                         "id": 1,
                                         "puntuacion": 10,
                                         "titulo": "La mejor maestra",
                                         "resenha": "Aprender manicura con ella ha sido una experiencia increíble.
                                            Es paciente, detallista y transmite su pasión por el cuidado de las uñas
                                            con cada explicación. Gracias a su ella, he ganado confianza y técnica.
                                            ¡Una excelente maestra y profesional!",
                                         "usuarioEscritor": {
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
                                             "descripcionProfesional": "Profesora de arte con enfoque en pintura
                                                abstracta. Más de 7 aÃ±os de experiencia en talleres comunitarios y
                                                proyectos escolares.",
                                             "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado 
                                                artístico y expresarse a través de los colores."
                                         },
                                         "usuarioValorado": {
                                             "id": "d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007",
                                             "username": "MalenG",
                                             "password": "{noop}D3SM3L3N4D4",
                                             "email": "malena.garcia@gmail.com",
                                             "nombre": "Malena",
                                             "apellidos": "García Urquiza",
                                             "fechaNacimiento": "1992-08-15",
                                             "sexo": "MUJER",
                                             "modalidadPreferida": "AMBAS",
                                             "numTelefono": "+5491122334455",
                                             "mostrarNumTelefono": false,
                                             "color": "#FF0F57",
                                             "imagenPerfil": "https://plus.unsplash.com/premium_photo-1661892088256-0a17130b3d0d?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cGVycml0b3xlbnwwfHwwfHx8MA%3D%3D",
                                             "idiomaNativo": "es",
                                             "descripcionProfesional": "Graduada en Veterinaria, con especializaciones 
                                                en dermatología animal y comportamiento felino. También cuenta con 
                                                estudios de estética y cuidado personal.",
                                             "presentacionPersonal": "Soy una persona optimista, amante de los animales 
                                                y la belleza. Disfruto enseñando lo que sé y aprendiendo de los demás."
                                         },
                                         "intercambio": {
                                             "intercambioID": 3,
                                             "estado": "FINALIZADO",
                                             "finalizadoPorDemandante": true,
                                             "finalizadoPorSolicitado": true,
                                             "fechaSolicitud": "2025-05-28T12:45:00",
                                             "fechaComienzo": "2025-05-29T09:10:00",
                                             "fechaFin": "2025-06-04T19:10:00",
                                             "talentoSolicitado": {
                                                 "id": 10,
                                                 "titulo": "Manicura y nail art profesional",
                                                 "descripcion": "Aprende técnicas de manicura moderna, cuidado de uñas 
                                                    y decoración creativa. Incluye consejos de higiene y estética.",
                                                 "imagen": null,
                                                 "nivel": {
                                                     "id": 4,
                                                     "nombre": "Avanzado",
                                                     "color": "#ff9500",
                                                     "orden": 4
                                                 }
                                             },
                                             "talentoAceptado": {
                                                 "id": 4,
                                                 "titulo": "Pintura abstracta",
                                                 "descripcion": "Exploro formas, colores y emociones. Enseño técnicas 
                                                    modernas y expresión libre.",
                                                 "imagen": "https://i.pinimg.com/736x/61/98/79/619879c242922d0f7f317d63095d1920.jpg",
                                                 "nivel": {
                                                     "id": 4,
                                                     "nombre": "Avanzado",
                                                     "color": "#ff9500",
                                                     "orden": 4
                                                 }
                                             },
                                             "talentoSugerido": {
                                                 "id": 5,
                                                 "titulo": "Introducción al bordado",
                                                 "descripcion": "Curso básico de técnicas de bordado tradicional, 
                                                    ideal para principiantes que quieren aprender a crear sus primeros 
                                                    diseños a mano.",
                                                 "imagen": null,
                                                 "nivel": {
                                                     "id": 2,
                                                     "nombre": "BÃ¡sico",
                                                     "color": "#009deb",
                                                     "orden": 2
                                                 }
                                             }
                                         }
                                     },
                                     {
                                         "id": 2,
                                         "puntuacion": 8,
                                         "titulo": null,
                                         "resenha": "Su enfoque para enseñar pintura abstracta es muy interesante y me 
                                            ha ayudado a soltarme creativamente. Aunque a veces me costaba seguir el 
                                            ritmo, aprendí nuevas formas de expresarme y explorar el color. Una 
                                            experiencia enriquecedora.",
                                         "usuarioEscritor": {
                                             "id": "d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007",
                                             "username": "MalenG",
                                             "password": "{noop}D3SM3L3N4D4",
                                             "email": "malena.garcia@gmail.com",
                                             "nombre": "Malena",
                                             "apellidos": "García Urquiza",
                                             "fechaNacimiento": "1992-08-15",
                                             "sexo": "MUJER",
                                             "modalidadPreferida": "AMBAS",
                                             "numTelefono": "+5491122334455",
                                             "mostrarNumTelefono": false,
                                             "color": "#FF0F57",
                                             "imagenPerfil": "https://plus.unsplash.com/premium_photo-1661892088256-0a17130b3d0d?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cGVycml0b3xlbnwwfHwwfHx8MA%3D%3D",
                                             "idiomaNativo": "es",
                                             "descripcionProfesional": "Graduada en Veterinaria, con especializaciones 
                                                en dermatología animal y comportamiento felino. También cuenta con 
                                                estudios de estética y cuidado personal.",
                                             "presentacionPersonal": "Soy una persona optimista, amante de los animales 
                                                y la belleza. Disfruto enseñando lo que sé y aprendiendo de los demás."
                                         },
                                         "usuarioValorado": {
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
                                             "descripcionProfesional": "Profesora de arte con enfoque en pintura 
                                                abstracta. Más de 7 años de experiencia en talleres comunitarios y 
                                                proyectos escolares.",
                                             "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado 
                                                artístico y expresarse a través de los colores."
                                         },
                                         "intercambio": {
                                             "intercambioID": 3,
                                             "estado": "FINALIZADO",
                                             "finalizadoPorDemandante": true,
                                             "finalizadoPorSolicitado": true,
                                             "fechaSolicitud": "2025-05-28T12:45:00",
                                             "fechaComienzo": "2025-05-29T09:10:00",
                                             "fechaFin": "2025-06-04T19:10:00",
                                             "talentoSolicitado": {
                                                 "id": 10,
                                                 "titulo": "Manicura y nail art profesional",
                                                 "descripcion": "Aprende técnicas de manicura moderna, cuidado de uñas 
                                                    y decoración creativa. Incluye consejos de higiene y estética.",
                                                 "imagen": null,
                                                 "nivel": {
                                                     "id": 4,
                                                     "nombre": "Avanzado",
                                                     "color": "#ff9500",
                                                     "orden": 4
                                                 }
                                             },
                                             "talentoAceptado": {
                                                 "id": 4,
                                                 "titulo": "Pintura abstracta",
                                                 "descripcion": "Exploro formas, colores y emociones. Enseño técnicas 
                                                    modernas y expresión libre.",
                                                 "imagen": "https://i.pinimg.com/736x/61/98/79/619879c242922d0f7f317d63095d1920.jpg",
                                                 "nivel": {
                                                     "id": 4,
                                                     "nombre": "Avanzado",
                                                     "color": "#ff9500",
                                                     "orden": 4
                                                 }
                                             },
                                             "talentoSugerido": {
                                                 "id": 5,
                                                 "titulo": "Introducción al bordado",
                                                 "descripcion": "Curso básico de técnicas de bordado tradicional, ideal 
                                                    para principiantes que quieren aprender a crear sus primeros 
                                                    diseños a mano.",
                                                 "imagen": null,
                                                 "nivel": {
                                                     "id": 2,
                                                     "nombre": "Básico",
                                                     "color": "#009deb",
                                                     "orden": 2
                                                 }
                                             }
                                         }
                                    }
                                ],
                                "pageable": {
                                    "sort": {
                                        "sorted": false,
                                        "unsorted": true,
                                        "empty": true
                                    },
                                    "pageNumber": 0,
                                    "pageSize": 10,
                                    "offset": 0,
                                    "unpaged": false,
                                    "paged": true
                                },
                                "totalElements": 1,
                                "totalPages": 1,
                                "last": true,
                                "size": 10,
                                "number": 0,
                                "sort": {
                                    "sorted": false,
                                    "unsorted": true,
                                    "empty": true
                                },
                                "numberOfElements": 1,
                                "first": true,
                                "empty": false
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Usuario no encontrado.",
            content = @Content)
    })
    @GetMapping("listar")
    public Page<GetValoracionDTOConUsersEIntercambio> findValoracionesFromUsuario(
        @AuthenticationPrincipal User usuarioAutenticado, Pageable pageable) {
        Page<Valoracion> listaValoraciones =
            valoracionService.findValoracionesFromUsuario(usuarioAutenticado.getId(), pageable);

        List<GetValoracionDTOConUsersEIntercambio> listaValoracionesConDTO = listaValoraciones.stream()
            .map(valoracion -> GetValoracionDTOConUsersEIntercambio.of(
                valoracion,
                GetUserDTO.of(valoracion.getUsuarioEscritor()),
                GetUserDTO.of(valoracion.getUsuarioValorado()),
                GetIntercambioDTOSinUsers.of(
                    valoracion.getIntercambio(),
                    GetTalentoDTOConNivel.of(valoracion.getIntercambio().getTalentoSolicitado(),
                        GetNivelDTO.of(
                            talentoService.getNivelByTalentoID(valoracion.getIntercambio().getTalentoSolicitado().getId())
                        )
                    ),
                    valoracion.getIntercambio().getTalentoAceptado() != null ?
                        GetTalentoDTOConNivel.of(valoracion.getIntercambio().getTalentoAceptado(),
                            GetNivelDTO.of(
                                talentoService.getNivelByTalentoID(valoracion.getIntercambio().getTalentoAceptado().getId())
                            )
                        ) : null,
                    GetTalentoDTOConNivel.of(valoracion.getIntercambio().getTalentoSugerido(),
                        GetNivelDTO.of(
                            talentoService.getNivelByTalentoID(valoracion.getIntercambio().getTalentoSugerido().getId())
                        )
                    )
                )
            )
        ).toList();

        return new PageImpl<>(listaValoracionesConDTO, pageable, listaValoraciones.getTotalElements());
    }

    @Operation(summary = "Edita una valoración ya existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Se ha editado la Valoración correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetValoracionDTOConUsersEIntercambio.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                 "puntuacion": 5,
                                 "titulo": "Mejorable...",
                                 "resenha": "Ha faltado a algunas sesiones acordadas."
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
            description = "Usuario no autorizado para editar esta Valoración.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Valoración no encontrada.",
            content = @Content)
    })
    @PutMapping("/{valoracionID}/editar")
    public ResponseEntity<GetValoracionDTOConUsersEIntercambio> editarValoracion(@AuthenticationPrincipal User usuarioEscritor,
        @Valid @RequestBody CreateValoracionCMD valoracionCMD, @PathVariable Long valoracionID) {
        Valoracion valoracionEditada = valoracionService.editarValoracion(valoracionID, valoracionCMD, usuarioEscritor);

        return ResponseEntity.ok(
            GetValoracionDTOConUsersEIntercambio.of(
                valoracionEditada,
                GetUserDTO.of(valoracionEditada.getUsuarioEscritor()),
                GetUserDTO.of(valoracionEditada.getUsuarioValorado()),
                GetIntercambioDTOSinUsers.of(
                    valoracionEditada.getIntercambio(),
                    GetTalentoDTOConNivel.of(valoracionEditada.getIntercambio().getTalentoSolicitado(),
                        GetNivelDTO.of(talentoService.getNivelByTalentoID(valoracionEditada.getIntercambio().getTalentoSolicitado().getId()))
                    ),
                    valoracionEditada.getIntercambio().getTalentoAceptado() != null ?
                        GetTalentoDTOConNivel.of(valoracionEditada.getIntercambio().getTalentoAceptado(),
                            GetNivelDTO.of(talentoService.getNivelByTalentoID(valoracionEditada.getIntercambio().getTalentoAceptado().getId()))
                        ) : null,
                    GetTalentoDTOConNivel.of(valoracionEditada.getIntercambio().getTalentoSugerido(),
                        GetNivelDTO.of(talentoService.getNivelByTalentoID(valoracionEditada.getIntercambio().getTalentoSugerido().getId()))
                    )
                )
            )
        );
    }

}
