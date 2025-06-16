package com.example.APPbility.controller;

import com.example.APPbility.dto.intercambio.*;
import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.service.IntercambioService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/intercambio/")
@Tag(name = "Intercambio", description = "Controlador de Intercambio, para poder realizar sus operaciones de gestión.")
public class IntercambioController {

    private final IntercambioService intercambioService;
    private final TalentoService talentoService;

    @Operation(summary = "Propone un nuevo Intercambio.",
        description = "Se crea un Intercambio con estado PROPUESTO por el Usuario Demandante a un Usuario solicitado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Intercambio propuesto correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetIntercambioDTOParaProponer.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "intercambioID": 7,
                                "estado": "PROPUESTO",
                                "fechaSolicitud": "2025-06-15T19:34:10.3060986",
                                "usuarioDemandante": {
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
                                    "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.",
                                    "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores."
                                },
                                "usuarioSolicitado": {
                                    "id": "123e4567-e89b-12d3-a456-426614174000",
                                    "username": "Khin90",
                                    "password": "{noop}khin",
                                    "email": "khindasvinto@gmail.com",
                                    "nombre": "Khindasvinto",
                                    "apellidos": "Batbayar Gaanbatar",
                                    "fechaNacimiento": "1990-01-01",
                                    "sexo": "HOMBRE",
                                    "modalidadPreferida": "VIRTUAL",
                                    "numTelefono": "+34123456789",
                                    "mostrarNumTelefono": false,
                                    "color": "#6A7FDE",
                                    "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                    "idiomaNativo": "es",
                                    "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020 - noviembre 2021.",
                                    "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                },
                                "talentoSolicitado": {
                                    "id": 1,
                                    "titulo": "Karate",
                                    "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.",
                                    "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                    "nivel": {
                                        "id": 5,
                                        "nombre": "Experto",
                                        "color": "#e80044",
                                        "orden": 5
                                    }
                                },
                                "talentoSugerido": {
                                    "id": 5,
                                    "titulo": "Introducción al bordado",
                                    "descripcion": "Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.",
                                    "imagen": null,
                                    "nivel": {
                                        "id": 2,
                                        "nombre": "Básico",
                                        "color": "#009deb",
                                        "orden": 2
                                    }
                                }
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
        @ApiResponse(responseCode = "404",
            description = "Usuario o Talento no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "Un Usuario no puede proponerse un Intercambio a sí mismo",
            content = @Content)
    })
    @PostMapping("proponer")
    public ResponseEntity<GetIntercambioDTOParaProponer> proponerIntercambio(@AuthenticationPrincipal User usuarioDemandante,
        @Valid @RequestBody CreateIntercambioCMD nuevo) {
        Intercambio intercambioPropuesto = intercambioService.proponerIntercambio(nuevo, usuarioDemandante);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            GetIntercambioDTOParaProponer.of(
                intercambioPropuesto,
                GetUserDTO.of(usuarioDemandante),
                GetUserDTO.of(intercambioPropuesto.getUsuarioSolicitado()),
                GetTalentoDTOConNivel.of(
                    intercambioPropuesto.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioPropuesto.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(
                    intercambioPropuesto.getTalentoSugerido(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioPropuesto.getTalentoSugerido().getId()))
                )
            )
        );
    }

    @Operation(summary = "Cancela un Intercambio propuesto.",
        description = "Cancela un Intercambio con estado PROPUESTO por el Usuario demandante.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Intercambio cancelado correctamente.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para cancelar este Intercambio.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "El Intercambio no se encuentra en estado PROPUESTO.",
            content = @Content)
    })
    @DeleteMapping("cancelar/{id}")
    public ResponseEntity<?> cancelarIntercambioPropuesto(@AuthenticationPrincipal User usuarioDemandante,
        @PathVariable Long id) {
        intercambioService.cancelarIntercambioPropuesto(id, usuarioDemandante);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Acepta un Intercambio.",
        description = "Un Usuario solicitado acepta un intercambio con estado PROPUESTO.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Intercambio aceptado correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetIntercambioDTOAlAceptar.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                 "intercambioID": 7,
                                 "estado": "ACTIVO",
                                 "fechaSolicitud": "2025-06-15T19:34:10.306099",
                                 "fechaComienzo": "2025-06-15T20:00:23.4988749",
                                 "usuarioDemandante": {
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
                                     "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.",
                                     "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores."
                                 },
                                 "usuarioSolicitado": {
                                     "id": "123e4567-e89b-12d3-a456-426614174000",
                                     "username": "Khin90",
                                     "password": "{noop}khin",
                                     "email": "khindasvinto@gmail.com",
                                     "nombre": "Khindasvinto",
                                     "apellidos": "Batbayar Gaanbatar",
                                     "fechaNacimiento": "1990-01-01",
                                     "sexo": "HOMBRE",
                                     "modalidadPreferida": "VIRTUAL",
                                     "numTelefono": "+34123456789",
                                     "mostrarNumTelefono": false,
                                     "color": "#6A7FDE",
                                     "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                     "idiomaNativo": "es",
                                     "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020- noviembre 2021.",
                                     "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                 },
                                 "talentoSolicitado": {
                                     "id": 1,
                                     "titulo": "Karate",
                                     "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.",
                                     "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                     "nivel": {
                                         "id": 5,
                                         "nombre": "Experto",
                                         "color": "#e80044",
                                         "orden": 5
                                     }
                                 },
                                 "talentoAceptado": {
                                     "id": 4,
                                     "titulo": "Pintura abstracta",
                                     "descripcion": "Exploro formas, colores y emociones. Enseño técnicas modernas y expresión libre.",
                                     "imagen": "https://i.pinimg.com/736x/61/98/79/619879c242922d0f7f317d63095d1920.jpg",
                                     "nivel": {
                                         "id": 4,
                                         "nombre": "Avanzado",
                                         "color": "#ff9500",
                                         "orden": 4
                                     }
                                 }
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
            description = "Usuario no autorizado para aceptar este Intercambio.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio o Talento no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "El Intercambio no se encuentra en estado PROPUESTO.",
            content = @Content)
    })
    @PutMapping("aceptar/{id}")
    public ResponseEntity<GetIntercambioDTOAlAceptar> aceptarIntercambio(@AuthenticationPrincipal User usuarioSolicitado,
        @Valid @RequestBody AceptarIntercambioCMD intercambioCMD, @PathVariable Long id) {
        Intercambio intercambioAceptado = intercambioService.aceptarIntercambio(id, intercambioCMD, usuarioSolicitado);

        return ResponseEntity.ok(
            GetIntercambioDTOAlAceptar.of(
                intercambioAceptado,
                GetUserDTO.of(intercambioAceptado.getUsuarioDemandante()),
                GetUserDTO.of(usuarioSolicitado),
                GetTalentoDTOConNivel.of(
                    intercambioAceptado.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioAceptado.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(
                    intercambioAceptado.getTalentoAceptado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioAceptado.getTalentoAceptado().getId()))
                )
            )
        );
    }

    @Operation(summary = "Rechaza un intercambio.",
        description = "Un Usuario solicitado rechaza un intercambio con estado PROPUESTO.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Intercambio rechazado correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetIntercambioDTOAlRechazar.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "intercambioID": 7,
                                "estado": "RECHAZADO",
                                "fechaSolicitud": "2025-06-15T21:10:11.451104",
                                "usuarioDemandante": {
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
                                    "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.",
                                    "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores."
                                },
                                "usuarioSolicitado": {
                                    "id": "123e4567-e89b-12d3-a456-426614174000",
                                    "username": "Khin90",
                                    "password": "{noop}khin",
                                    "email": "khindasvinto@gmail.com",
                                    "nombre": "Khindasvinto",
                                    "apellidos": "Batbayar Gaanbatar",
                                    "fechaNacimiento": "1990-01-01",
                                    "sexo": "HOMBRE",
                                    "modalidadPreferida": "VIRTUAL",
                                    "numTelefono": "+34123456789",
                                    "mostrarNumTelefono": false,
                                    "color": "#6A7FDE",
                                    "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                    "idiomaNativo": "es",
                                    "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020 - noviembre 2021.",
                                    "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                },
                                "talentoSolicitado": {
                                    "id": 1,
                                    "titulo": "Karate",
                                    "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.",
                                    "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                    "nivel": {
                                        "id": 5,
                                        "nombre": "Experto",
                                        "color": "#e80044",
                                        "orden": 5
                                    }
                                },
                                "talentoSugerido": {
                                    "id": 5,
                                    "titulo": "Introducción al bordado",
                                    "descripcion": "Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.",
                                    "imagen": null,
                                    "nivel": {
                                        "id": 2,
                                        "nombre": "Básico",
                                        "color": "#009deb",
                                        "orden": 2
                                    }
                                }
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para rechazar este intercambio.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "El Intercambio no se encuentra en estado PROPUESTO.",
            content = @Content)
    })
    @PutMapping("rechazar/{id}")
    public ResponseEntity<GetIntercambioDTOAlRechazar> rechazarIntercambio(@AuthenticationPrincipal User usuarioSolicitado,
        @PathVariable Long id) {
        Intercambio intercambioRechazado = intercambioService.rechazarIntercambio(id, usuarioSolicitado);

        return ResponseEntity.ok(
            GetIntercambioDTOAlRechazar.of(
                intercambioRechazado,
                GetUserDTO.of(intercambioRechazado.getUsuarioDemandante()),
                GetUserDTO.of(usuarioSolicitado),
                GetTalentoDTOConNivel.of(
                    intercambioRechazado.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioRechazado.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(
                    intercambioRechazado.getTalentoSugerido(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambioRechazado.getTalentoSugerido().getId()))
                )
            )
        );
    }

    @Operation(summary = "Devuelve los detalles de un Intercambio buscado por ID.",
        description = "Devuelve los detalles completos de un Intercambio específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Intercambio encontrado.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetIntercambioDTOConUsersYTalentos.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                 "intercambioID": 7,
                                 "estado": "ACTIVO",
                                 "finalizadoPorDemandante": false,
                                 "finalizadoPorSolicitado": false,
                                 "fechaSolicitud": "2025-06-15T19:34:10.306099",
                                 "fechaComienzo": "2025-06-15T20:00:23.498875",
                                 "fechaFin": null,
                                 "usuarioDemandante": {
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
                                     "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.",
                                     "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores."
                                 },
                                 "usuarioSolicitado": {
                                     "id": "123e4567-e89b-12d3-a456-426614174000",
                                     "username": "Khin90",
                                     "password": "{noop}khin",
                                     "email": "khindasvinto@gmail.com",
                                     "nombre": "Khindasvinto",
                                     "apellidos": "Batbayar Gaanbatar",
                                     "fechaNacimiento": "1990-01-01",
                                     "sexo": "HOMBRE",
                                     "modalidadPreferida": "VIRTUAL",
                                     "numTelefono": "+34123456789",
                                     "mostrarNumTelefono": false,
                                     "color": "#6A7FDE",
                                     "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                     "idiomaNativo": "es",
                                     "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020 - noviembre 2021.",
                                     "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                 },
                                 "talentoSolicitado": {
                                     "id": 1,
                                     "titulo": "Karate",
                                     "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.",
                                     "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                     "nivel": {
                                         "id": 5,
                                         "nombre": "Experto",
                                         "color": "#e80044",
                                         "orden": 5
                                     }
                                 },
                                 "talentoAceptado": {
                                     "id": 4,
                                     "titulo": "Pintura abstracta",
                                     "descripcion": "Exploro formas, colores y emociones. Enseño técnicas modernas y expresión libre.",
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
                                     "descripcion": "Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.",
                                     "imagen": null,
                                     "nivel": {
                                         "id": 2,
                                         "nombre": "Básico",
                                         "color": "#009deb",
                                         "orden": 2
                                     }
                                 }
                             }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para ver los detalles de este Intercambio.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio no encontrado.",
            content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<GetIntercambioDTOConUsersYTalentos> verDetallesDeIntercambio(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable Long id) {
        Intercambio intercambio = intercambioService.verDetallesDeIntercambio(id, usuarioAutenticado);

        return ResponseEntity.ok(
            GetIntercambioDTOConUsersYTalentos.of(
                intercambio,
                GetUserDTO.of(intercambio.getUsuarioDemandante()),
                GetUserDTO.of(intercambio.getUsuarioSolicitado()),
                GetTalentoDTOConNivel.of(intercambio.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSolicitado().getId()))
                ),
                intercambio.getTalentoAceptado() != null ?
                    GetTalentoDTOConNivel.of(intercambio.getTalentoAceptado(),
                        GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoAceptado().getId()))
                    ) : null,
                GetTalentoDTOConNivel.of(intercambio.getTalentoSugerido(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSugerido().getId()))
                )
            )
        );
    }

    @Operation(summary = "Devuelve una lista paginada de todos los Intercambios de un Usuario autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de Intercambios obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Page.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "content": [
                                    {
                                        "intercambioID": 1,
                                        "estado": "PROPUESTO",
                                        "finalizadoPorDemandante": false,
                                        "finalizadoPorSolicitado": false,
                                        "fechaSolicitud": "2025-04-22T10:45:00",
                                        "fechaComienzo": null,
                                        "fechaFin": null,
                                        "usuarioDemandante": {
                                            "id": "123e4567-e89b-12d3-a456-426614174000",
                                            "username": "Khin90",
                                            "password": "{noop}khin",
                                            "email": "khindasvinto@gmail.com",
                                            "nombre": "Khindasvinto",
                                            "apellidos": "Batbayar Gaanbatar",
                                            "fechaNacimiento": "1990-01-01",
                                            "sexo": "HOMBRE",
                                            "modalidadPreferida": "VIRTUAL",
                                            "numTelefono": "+34123456789",
                                            "mostrarNumTelefono": false,
                                            "color": "#6A7FDE",
                                            "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                            "idiomaNativo": "es",
                                            "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecologíaa, mayo 2020 - noviembre 2021.",
                                            "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                        },
                                        "usuarioSolicitado": {
                                            "id": "9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00",
                                            "username": "Arman",
                                            "password": "{noop}timihaki",
                                            "email": "armanharutyunyan@gmail.com",
                                            "nombre": "Tigran Miqayel",
                                            "apellidos": "Harutyunyan Kirakosyan",
                                            "fechaNacimiento": "1990-08-14",
                                            "sexo": "HOMBRE",
                                            "modalidadPreferida": "PRESENCIAL",
                                            "numTelefono": "+37477123456",
                                            "mostrarNumTelefono": true,
                                            "color": "#CD0070",
                                            "imagenPerfil": "https://img.freepik.com/foto-gratis/retrato-hombre-sonriente-posando-al-aire-libre_23-2148803564.jpg",
                                            "idiomaNativo": "hy",
                                            "descripcionProfesional": "Apasionado por la biología, la cultura gastronómica armenia y los métodos científicos tradicionales.",
                                            "presentacionPersonal": "Curioso, dedicado y a veces un poco terco. Pero siempre con ganas de compartir conocimientos."
                                        },
                                        "talentoSolicitado": {
                                            "id": 7,
                                            "titulo": "Cocina tradicional armenia",
                                            "descripcion": "Aprende a preparar platos clÃ¡sicos como el khorovats, dolma o harissa. Conoce los secretos de las especias armenias y la historia detrÃ¡s de cada receta.",
                                            "imagen": "https://www.seriouseats.com/thmb/B7k0_4SlsrJBmzbQcBp4f6NTCTg=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/20230106-SHISH-KEBAB-ANDREW-JANJIGIAN-25-09d222b8b2764cfdb058e5ac592a99c4.jpg",
                                            "nivel": {
                                                "id": 2,
                                                "nombre": "BÃ¡sico",
                                                "color": "#009deb",
                                                "orden": 2
                                            }
                                        },
                                        "talentoAceptado": null,
                                        "talentoSugerido": {
                                            "id": 3,
                                            "titulo": "Inglés para conversar",
                                            "descripcion": "He vivido 3 años en Reino Unido y puedo ayudar a mejorar la fluidez, pronunciación y vocabulario en inglés.",
                                            "imagen": null,
                                            "nivel": {
                                                "id": 3,
                                                "nombre": "Intermedio",
                                                "color": "#6dd702",
                                                "orden": 3
                                            }
                                        }
                                    },
                                    {
                                         "intercambioID": 7,
                                         "estado": "ACTIVO",
                                         "finalizadoPorDemandante": false,
                                         "finalizadoPorSolicitado": false,
                                         "fechaSolicitud": "2025-06-15T19:34:10.306099",
                                         "fechaComienzo": "2025-06-15T20:00:23.498875",
                                         "fechaFin": null,
                                         "usuarioDemandante": {
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
                                             "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.",
                                             "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores."
                                         },
                                         "usuarioSolicitado": {
                                             "id": "123e4567-e89b-12d3-a456-426614174000",
                                             "username": "Khin90",
                                             "password": "{noop}khin",
                                             "email": "khindasvinto@gmail.com",
                                             "nombre": "Khindasvinto",
                                             "apellidos": "Batbayar Gaanbatar",
                                             "fechaNacimiento": "1990-01-01",
                                             "sexo": "HOMBRE",
                                             "modalidadPreferida": "VIRTUAL",
                                             "numTelefono": "+34123456789",
                                             "mostrarNumTelefono": false,
                                             "color": "#6A7FDE",
                                             "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                             "idiomaNativo": "es",
                                             "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020- noviembre 2021.",
                                             "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                         },
                                         "talentoSolicitado": {
                                             "id": 1,
                                             "titulo": "Karate",
                                             "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.",
                                             "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                             "nivel": {
                                                 "id": 5,
                                                 "nombre": "Experto",
                                                 "color": "#e80044",
                                                 "orden": 5
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
                                             "descripcion": "Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.",
                                             "imagen": null,
                                             "nivel": {
                                                 "id": 2,
                                                 "nombre": "Básico",
                                                 "color": "#009deb",
                                                 "orden": 2
                                             }
                                         }
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
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content)
    })
    @GetMapping("mis-intercambios")
    public Page<GetIntercambioDTOConUsersYTalentos> findIntercambiosfromUsuario(@PageableDefault(sort = "id",
        direction = Sort.Direction.ASC) Pageable pageable, @AuthenticationPrincipal User usuarioAutenticado) {
        Page<Intercambio> listaIntercambios = intercambioService.findIntercambiosFromUsuario(usuarioAutenticado, pageable);

        List<GetIntercambioDTOConUsersYTalentos> listaIntercambiosConDTO = listaIntercambios.stream()
            .map(intercambio -> GetIntercambioDTOConUsersYTalentos.of(
                    intercambio,
                    GetUserDTO.of(intercambio.getUsuarioDemandante()),
                    GetUserDTO.of(intercambio.getUsuarioSolicitado()),
                    GetTalentoDTOConNivel.of(intercambio.getTalentoSolicitado(),
                        GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSolicitado().getId()))
                    ),
                    intercambio.getTalentoAceptado() != null ?
                        GetTalentoDTOConNivel.of(intercambio.getTalentoAceptado(),
                            GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoAceptado().getId()))
                        ) : null,
                    GetTalentoDTOConNivel.of(intercambio.getTalentoSugerido(),
                        GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSugerido().getId()))
                    )
                )
            ).toList();

        return new PageImpl<>(listaIntercambiosConDTO, pageable, listaIntercambios.getTotalElements());
    }

    @Operation(summary = "Finaliza un intercambio.",
        description = "Marca un intercambio con estado ACEPTADO como FINALIZADO por cualquiera de los Usuarios " +
            "participantes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Intercambio finalizado correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetIntercambioDTOConUsersYTalentos.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                 "intercambioID": 7,
                                 "estado": "FINALIZADO",
                                 "finalizadoPorDemandante": true,
                                 "finalizadoPorSolicitado": true,
                                 "fechaSolicitud": "2025-06-15T19:34:10.306099",
                                 "fechaComienzo": "2025-06-15T20:00:23.498875",
                                 "fechaFin": "2025-06-15T20:52:25.3163661",
                                 "usuarioDemandante": {
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
                                     "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.",
                                     "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores."
                                 },
                                 "usuarioSolicitado": {
                                     "id": "123e4567-e89b-12d3-a456-426614174000",
                                     "username": "Khin90",
                                     "password": "{noop}khin",
                                     "email": "khindasvinto@gmail.com",
                                     "nombre": "Khindasvinto",
                                     "apellidos": "Batbayar Gaanbatar",
                                     "fechaNacimiento": "1990-01-01",
                                     "sexo": "HOMBRE",
                                     "modalidadPreferida": "VIRTUAL",
                                     "numTelefono": "+34123456789",
                                     "mostrarNumTelefono": false,
                                     "color": "#6A7FDE",
                                     "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                     "idiomaNativo": "es",
                                     "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020- noviembre 2021.",
                                     "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                 },
                                 "talentoSolicitado": {
                                     "id": 1,
                                     "titulo": "Karate",
                                     "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.",
                                     "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                     "nivel": {
                                         "id": 5,
                                         "nombre": "Experto",
                                         "color": "#e80044",
                                         "orden": 5
                                     }
                                 },
                                 "talentoAceptado": {
                                     "id": 4,
                                     "titulo": "Pintura abstracta",
                                     "descripcion": "Exploro formas, colores y emociones. Enseño técnicas modernas y
                                        expresión libre.",
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
                                     "descripcion": "Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.",
                                     "imagen": null,
                                     "nivel": {
                                         "id": 2,
                                         "nombre": "Básico",
                                         "color": "#009deb",
                                         "orden": 2
                                     }
                                 }
                             }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para dar por finalizado este Intercambio.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "El Intercambio no se encuentra en estado ACTIVO.",
            content = @Content)
    })
    @PutMapping("finalizar/{id}")
    public ResponseEntity<GetIntercambioDTOConUsersYTalentos> finalizarIntercambio( @AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable Long id) {
        Intercambio intercambio = intercambioService.finalizarIntercambio(id, usuarioAutenticado);

        return ResponseEntity.ok(
            GetIntercambioDTOConUsersYTalentos.of(
                intercambio,
                GetUserDTO.of(intercambio.getUsuarioDemandante()),
                GetUserDTO.of(intercambio.getUsuarioSolicitado()),
                GetTalentoDTOConNivel.of(intercambio.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(intercambio.getTalentoAceptado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoAceptado().getId()))
                ),
                GetTalentoDTOConNivel.of(intercambio.getTalentoSugerido(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSugerido().getId()))
                )
            )
        );
    }

    @Operation(summary = "Deshace la finalización de un intercambio.",
        description = "Deshace la finalización de un Intercambio con estado ACTIVO por parte de uno de los dos Usuarios " +
            "participantes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Finalización deshecha exitosamente",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetIntercambioDTOConUsersYTalentos.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                 "intercambioID": 7,
                                 "estado": "FINALIZADO",
                                 "finalizadoPorDemandante": true,
                                 "finalizadoPorSolicitado": false,
                                 "fechaSolicitud": "2025-06-15T19:34:10.306099",
                                 "fechaComienzo": "2025-06-15T20:00:23.498875",
                                 "fechaFin": "2025-06-15T20:52:25.3163661",
                                 "usuarioDemandante": {
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
                                     "descripcionProfesional": "Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.",
                                     "presentacionPersonal": "Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores."
                                 },
                                 "usuarioSolicitado": {
                                     "id": "123e4567-e89b-12d3-a456-426614174000",
                                     "username": "Khin90",
                                     "password": "{noop}khin",
                                     "email": "khindasvinto@gmail.com",
                                     "nombre": "Khindasvinto",
                                     "apellidos": "Batbayar Gaanbatar",
                                     "fechaNacimiento": "1990-01-01",
                                     "sexo": "HOMBRE",
                                     "modalidadPreferida": "VIRTUAL",
                                     "numTelefono": "+34123456789",
                                     "mostrarNumTelefono": false,
                                     "color": "#6A7FDE",
                                     "imagenPerfil": "https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg",
                                     "idiomaNativo": "es",
                                     "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020- noviembre 2021.",
                                     "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento."
                                 },
                                 "talentoSolicitado": {
                                     "id": 1,
                                     "titulo": "Karate",
                                     "descripcion": "Tal y como se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.",
                                     "imagen": "https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg",
                                     "nivel": {
                                         "id": 5,
                                         "nombre": "Experto",
                                         "color": "#e80044",
                                         "orden": 5
                                     }
                                 },
                                 "talentoAceptado": {
                                     "id": 4,
                                     "titulo": "Pintura abstracta",
                                     "descripcion": "Exploro formas, colores y emociones. Enseño técnicas modernas y expresión libre.",
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
                                     "descripcion": "Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.",
                                     "imagen": null,
                                     "nivel": {
                                         "id": 2,
                                         "nombre": "Básico",
                                         "color": "#009deb",
                                         "orden": 2
                                     }
                                 }
                             }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "Usuario no autorizado para deshacer un Intercambio como finalizado por su parte.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Intercambio no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "El Intercambio no se encuentra en estado ACTIVO.",
            content = @Content)
    })
    @PutMapping("deshacer-finalizacion/{id}")
    public ResponseEntity<GetIntercambioDTOConUsersYTalentos> deshacerFinalizacionDeIntercambioPorUsuario(
        @AuthenticationPrincipal User usuarioAutenticado, @PathVariable Long id) {
        Intercambio intercambio = intercambioService.deshacerFinalizacionDeIntercambioPorUsuario(id, usuarioAutenticado);

        return ResponseEntity.ok(
            GetIntercambioDTOConUsersYTalentos.of(
                intercambio,
                GetUserDTO.of(intercambio.getUsuarioDemandante()),
                GetUserDTO.of(intercambio.getUsuarioSolicitado()),
                GetTalentoDTOConNivel.of(intercambio.getTalentoSolicitado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSolicitado().getId()))
                ),
                GetTalentoDTOConNivel.of(intercambio.getTalentoAceptado(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoAceptado().getId()))
                ),
                GetTalentoDTOConNivel.of(intercambio.getTalentoSugerido(),
                    GetNivelDTO.of(talentoService.getNivelByTalentoID(intercambio.getTalentoSugerido().getId()))
                )
            )
        );
    }

}
