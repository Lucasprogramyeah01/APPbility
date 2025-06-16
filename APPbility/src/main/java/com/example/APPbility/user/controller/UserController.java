package com.example.APPbility.user.controller;

import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.security.jwt.access.JwtService;
import com.example.APPbility.security.jwt.refresh.RefreshToken;
import com.example.APPbility.security.jwt.refresh.RefreshTokenRequest;
import com.example.APPbility.security.jwt.refresh.RefreshTokenService;
import com.example.APPbility.user.dto.EditUserCMD;
import com.example.APPbility.user.dto.GetUserDTOConPaises;
//import com.example.APPbility.user.dto.GetUserDTOCompleto;
import com.example.APPbility.user.dto.seguridad.ActivateAccountRequest;
import com.example.APPbility.user.dto.seguridad.CreateUserRequest;
import com.example.APPbility.user.dto.seguridad.LoginRequest;
import com.example.APPbility.user.dto.seguridad.UserResponse;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.service.UserService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user/")
@Tag(name = "Usuario", description = "Controlador de Usuario, para poder realizar sus operaciones de gestión.")
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    //ENDPOINTS DEL CONTROLADOR --------------------------------------------------------------------------------

    @Operation(summary = "Devuelve una lista paginada de todos los Usuarios (con Rol: 'USER').",
        description = "Devuelve una lista paginada de todos los Usuarios con rol USER ordenados por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de Usuarios obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Page.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "content": [
                                    {
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
                                        "listaOtrosIdiomas": [
                                            "en",
                                            "fr"
                                        ],
                                        "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020 - noviembre 2021.",
                                        "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento.",
                                        "listaEnlacesExternos": [
                                            "https://x.com/",
                                            "https://www.linkedin.com/",
                                            "https://github.com/"
                                        ],
                                        "paisNativo": {
                                            "id": 1,
                                            "nombre": "Andorra",
                                            "codigoISO": "AD",
                                            "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                        },
                                        "paisResidencia": {
                                            "id": 1,
                                            "nombre": "Andorra",
                                            "codigoISO": "AD",
                                            "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                        }
                                    },
                                    {
                                        "id": "3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91",
                                        "username": "SophieML",
                                        "password": "{noop}sofiosasosa",
                                        "email": "sofia.martinez@gmail.com",
                                        "nombre": "Sofia",
                                        "apellidos": "Martínez López",
                                        "fechaNacimiento": "1993-04-12",
                                        "sexo": "MUJER",
                                        "modalidadPreferida": "PRESENCIAL",
                                        "numTelefono": "+34987654321",
                                        "mostrarNumTelefono": true,
                                        "color": "#FF0F57",
                                        "imagenPerfil": "https://randomuser.me/api/portraits/women/43.jpg",
                                        "idiomaNativo": "es",
                                        "listaOtrosIdiomas": [
                                            "en",
                                            "fr"
                                        ],
                                        "descripcionProfesional": "Ingeniera de software con especialización en inteligencia artificial. 5 años de experiencia en desarrollo de chatbots y modelos predictivos.",
                                        "presentacionPersonal": "Amante de la tecnología, el senderismo y la repostería. Siempre aprendiendo algo nuevo.",
                                        "listaEnlacesExternos": [
                                            "https://github.com/sofiaml"
                                        ],
                                        "paisNativo": {
                                            "id": 1,
                                            "nombre": "Andorra",
                                            "codigoISO": "AD",
                                            "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                        },
                                        "paisResidencia": {
                                            "id": 1,
                                            "nombre": "Andorra",
                                            "codigoISO": "AD",
                                            "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
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
            )})
    })
    @GetMapping
    public Page<GetUserDTOConPaises> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable){
        return userService.findAll(pageable)
        .map(user -> {
            GetPaisDTO paisNativoDTO = GetPaisDTO.of(user.getPaisNativo());
            GetPaisDTO paisResidenciaDTO = GetPaisDTO.of(user.getPaisResidencia());
            return GetUserDTOConPaises.of(user, paisNativoDTO, paisResidenciaDTO);
        });
    }

    @Operation(summary = "Devuelve un Usuario buscado por ID.",
        description = "Devuelve un Usuario incluyendo su país nativo y su país de residencia.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Usuario encontrado.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GetUserDTOConPaises.class),
                examples = {@ExampleObject(
                    value = """
                            {
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
                                 "listaOtrosIdiomas": [
                                     "en",
                                     "fr"
                                 ],
                                 "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020 - noviembre 2021.",
                                 "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento.",
                                 "listaEnlacesExternos": [
                                     "https://x.com/",
                                     "https://www.linkedin.com/",
                                     "https://github.com/"
                                 ],
                                 "paisNativo": {
                                     "id": 1,
                                     "nombre": "Andorra",
                                     "codigoISO": "AD",
                                     "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                 },
                                 "paisResidencia": {
                                     "id": 1,
                                     "nombre": "Andorra",
                                     "codigoISO": "AD",
                                     "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                 }
                             }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "404",
                description = "Usuario no encontrado.",
                content = @Content)
    })
    @GetMapping("{id}")
    public GetUserDTOConPaises findByID(@PathVariable UUID id){
        User user = userService.findById(id);

        return GetUserDTOConPaises.of(
            user,
            GetPaisDTO.of(user.getPaisNativo()),
            GetPaisDTO.of(user.getPaisResidencia())
        );
    }

    @Operation(summary = "Edita el perfil de un Usuario ya existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Se ha editado el Usuario correctamente.",
            content = { @Content(mediaType = "multipart/form-data",
                schema = @Schema(implementation = UserResponse.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                "username": "Khin90",
                                "rol": [
                                    "USER"
                                ],
                                "color": "#FF00CC"
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
            description = "Tipo de archivo no soportado.",
            content = @Content)
    })
    @PutMapping("editar")
    public ResponseEntity<UserResponse> edit(@AuthenticationPrincipal User usuarioAutenticado,
        @Valid @RequestPart("usuario") EditUserCMD editUserCMD,
        @RequestPart(value = "imagenPerfil", required = false) MultipartFile multipartFile) {
        User usuarioActualizado = userService.edit(usuarioAutenticado.getId(), editUserCMD, multipartFile);

        return ResponseEntity.ok(UserResponse.of(usuarioActualizado));
    }

    @Operation(summary = "Marca a un Usuario como favorito.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Usuario marcado como favorito correctamente.",
            content = { @Content(mediaType = "text/plain",
                    examples = {@ExampleObject(value = "Usuario añadido a favoritos correctamente.")}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Un Usuario no se puede marcar a sí mismo como favorito.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Usuario no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "Ese Usuario ya está en la lista de favoritos.",
            content = @Content)
    })
    @PostMapping("marcar/{favoritoID}/favorito")
    public ResponseEntity<String> marcarUsuarioComoFavorito(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable UUID favoritoID) {
        userService.marcarUsuarioComoFavorito(usuarioAutenticado.getId(), favoritoID);

        return ResponseEntity.ok("Usuario añadido a favoritos correctamente.");
    }

    @Operation(summary = "Devuelve una lista de todos los Usuarios favoritos de un Usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de Usuarios favoritos obtenida correctamente.",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = GetUserDTOConPaises.class)),
                examples = {@ExampleObject(
                    value = """
                            [
                                {
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
                                    "listaOtrosIdiomas": [
                                        "en",
                                        "fr"
                                    ],
                                    "descripcionProfesional": "Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020 - noviembre 2021.",
                                    "presentacionPersonal": "Boticario a tiempo completo y karateka de nacimiento.",
                                    "listaEnlacesExternos": [
                                        "https://x.com/",
                                        "https://www.linkedin.com/",
                                        "https://github.com/"
                                    ],
                                    "paisNativo": {
                                        "id": 1,
                                        "nombre": "Andorra",
                                        "codigoISO": "AD",
                                        "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                    },
                                    "paisResidencia": {
                                        "id": 1,
                                        "nombre": "Andorra",
                                        "codigoISO": "AD",
                                        "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png"
                                    }
                                }
                            ]
                            """
                )}
            )}),
        @ApiResponse(responseCode = "404",
            description = "Usuario no encontrado.",
            content = @Content)
    })
    @GetMapping("{usuarioID}/lista-favoritos")
    public Set<GetUserDTOConPaises> listarUsuariosFavoritos(@PathVariable UUID usuarioID) {
        Set<User> listaUsuariosFavoritos = userService.listarUsuariosFavoritos(usuarioID);

        Set<GetUserDTOConPaises> listaUsuariosFavoritosConDTO = listaUsuariosFavoritos.stream()
            .map(usuario -> GetUserDTOConPaises.of(
                usuario,
                GetPaisDTO.of(usuario.getPaisNativo()),
                GetPaisDTO.of(usuario.getPaisResidencia())
            ))
            .collect(Collectors.toSet());

        return listaUsuariosFavoritosConDTO;
    }

    @Operation(summary = "Desmarca a un Usuario de favoritos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Usuario eliminado de favoritos correctamente.",
            content = { @Content(mediaType = "text/plain",
                    examples = {@ExampleObject(value = "Usuario eliminado de favoritos correctamente.")}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Usuario no encontrado o no se encuentra en la lista de favoritos.",
            content = @Content)
    })
    @PostMapping("desmarcar/{favoritoID}/favorito")
    public ResponseEntity<String> desmarcarUsuarioDeFavoritos(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable UUID favoritoID) {
        userService.desmarcarUsuarioDeFavoritos(usuarioAutenticado.getId(), favoritoID);

        return ResponseEntity.ok("Usuario eliminado de favoritos correctamente.");
    }

    @Operation(summary = "Devuelve una lista con todos los Usuarios seguidores de un Usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de Usuarios seguidores obtenida correctamente.",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetUserDTOConPaises.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                 {
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
                                                     "listaOtrosIdiomas": [
                                                         "en",
                                                         "ru"
                                                     ],
                                                     "descripcionProfesional": "Apasionado por la biología, la cultura gastronómica armenia y los métodos científicos tradicionales.",
                                                     "presentacionPersonal": "Curioso, dedicado y a veces un poco terco. Pero siempre con ganas de compartir conocimientos.",
                                                     "listaEnlacesExternos": [
                                                         "https://www.linkedin.com/in/tigran-harutyunyan"
                                                     ],
                                                     "paisNativo": {
                                                         "id": 7,
                                                         "nombre": "Armenia",
                                                         "codigoISO": "AM",
                                                         "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/am.png"
                                                     },
                                                     "paisResidencia": {
                                                         "id": 8,
                                                         "nombre": "Angola",
                                                         "codigoISO": "AO",
                                                         "bandera": "https://flagpedia.net/data/flags/emoji/twitter/256x256/ao.png"
                                                     }
                                                 }
                                             ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Usuario no encontrado.",
                    content = @Content)
    })
    @GetMapping("{usuarioID}/lista-seguidores")
    public Set<GetUserDTOConPaises> listarUsuariosSeguidores(@PathVariable UUID usuarioID) {
        Set<User> listaUsuariosSeguidores = userService.listarUsuariosSeguidores(usuarioID);

        Set<GetUserDTOConPaises> listaUsuariosSeguidoresConDTO = listaUsuariosSeguidores.stream()
                .map(usuario -> GetUserDTOConPaises.of(
                        usuario,
                        GetPaisDTO.of(usuario.getPaisNativo()),
                        GetPaisDTO.of(usuario.getPaisResidencia())
                ))
                .collect(Collectors.toSet());

        return listaUsuariosSeguidoresConDTO;
    }

    @Operation(summary = "Calcula la media de puntuaciones de un Usuario.",
        description = "Calcula la media de las puntuaciones pertenecientes valoraciones recibidas por un Usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Media calculada correctamente.",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(type = "number", format = "double"),
                    examples = {@ExampleObject(value = "8.5")}
            )}),
        @ApiResponse(responseCode = "404",
            description = "Usuario no encontrado.",
            content = @Content)
    })
    @GetMapping("{usuarioID}/calcular-media")
    public ResponseEntity<Double> calcularMediaDePuntuacionesDeUsuario(@PathVariable UUID usuarioID) {
        Double media = userService.calcularMediaDePuntuacionesDeUsuario(usuarioID);
        return ResponseEntity.ok(media);
    }

    //ENDPOINTS RELACIONADOS CON SEGURIDAD ---------------------------------------------------------------------

    @Operation(summary = "Permite registrarse a un nuevo Usuario.",
        description = "Crea una nueva cuenta de Usuario en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Usuario registrado correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "1d2b5fa4-9b0f-4d8f-848a-a8b47994d929",
                                "username": "carlosrt89",
                                "rol": [
                                    "USER"
                                ],
                                "color": "#FF00CC"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Datos de entrada incorrectos.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "El username o el email ya se encuentran en uso.",
            content = @Content),
        @ApiResponse(responseCode = "415",
            description = "Tipo de archivo no soportado.",
            content = @Content)
    })
    @PostMapping("auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestPart("usuario") CreateUserRequest createUserRequest,
        @RequestPart(value = "imagenPerfil", required = false) MultipartFile multipartFile) {
        User user = userService.createUser(createUserRequest, multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(user));
    }

    @Operation(summary = "Permite iniciar sesión.",
        description = "Autentica a un usuario y genera un token de acceso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha iniciado sesión correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                "username": "Khin90",
                                "rol": [
                                    "USER"
                                ],
                                "color": "#6A7FDE",
                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjNlNDU2Ny1l...",
                                "refreshToken": "324067b6-254e-46af-916e-fe841e375f20"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Credenciales no válidas.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Autenticación fallida.",
            content = @Content)
    })
    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);

        // Generar el token de refresco.
        RefreshToken refreshToken = refreshTokenService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(UserResponse.of(user, accessToken, refreshToken.getToken()));
    }

    @Operation(summary = "Permite cerrar sesión.",
        description = "Invalida el token de un Usuario autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Sesión cerrada correctamente.",
            content = { @Content(mediaType = "text/plain",
                examples = {@ExampleObject(value = "Sesión cerrada correctamente.")}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content)
    })
    @PostMapping("auth/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal User usuarioAutenticado) {
        refreshTokenService.deleteAllByUser(usuarioAutenticado);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Sesión cerrada correctamente.");
    }

    @Operation(summary = "Refresca el token de un Usuario.",
        description = "Genera un nuevo token de acceso para un Usuario utilizando el token de refresco.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Token refrescado correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = RefreshTokenRequest.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                "username": "Khin90",
                                "rol": [
                                    "USER"
                                ],
                                "color": "#6A7FDE",
                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjNlND...",
                                "refreshToken": "f57e5b83-1c73-4ff5-b574-eced6b8dd155"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Token de refresco no válido.",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Token de refresco expirado.",
            content = @Content)
    })
    @PostMapping("auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED).body(refreshTokenService.refreshToken(token));
    }

    @Operation(summary = "Permite activar la cuenta de un Usuario registrado.",
        description = "Habilita la cuenta de un Usuario utilizando un token de activación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Se ha activado la cuenta correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "e6343523-73f6-4018-a879-34d85e4880dc",
                                "username": "carlosrt89",
                                "rol": [
                                    "USER"
                                ],
                                "color": "#FF00CC"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "400",
            description = "Token de activación no válido.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Usuario no encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "409",
            description = "La cuenta ya se encuentra activada.",
            content = @Content)
    })
    @PostMapping("activate/account/")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequest req) {
        String token = req.token();

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(userService.activateAccount(token)));
    }

    @Operation(summary = "Devuelve la información del Usuario (con Rol: 'USER' autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Datos de Usuario obtenidos correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                "username": "Khin90",
                                "rol": [
                                    "USER"
                                ],
                                "color": "#6A7FDE"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content)
    })
    @GetMapping("me")
    public UserResponse me(@AuthenticationPrincipal User user) {
        return UserResponse.of(user);
    }

    @Operation(summary = "Devuelve la información del Usuario (con Rol: 'ADMIN') autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Datos de Administrador obtenidos correctamente.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = {@ExampleObject(
                    value = """
                            {
                                "id": "551e8400-e22b-41d4-a716-446655440010",
                                "username": "admin",
                                "rol": [
                                    "ADMIN"
                                ],
                                "color": "#0F7BFF"
                            }
                            """
                )}
            )}),
        @ApiResponse(responseCode = "401",
            description = "Usuario no autenticado.",
            content = @Content),
        @ApiResponse(responseCode = "403",
            description = "El Usuario no tiene rol de ADMIN.",
            content = @Content)
    })
    @GetMapping("me/admin")
    public UserResponse adminMe(@AuthenticationPrincipal User user) {
        return UserResponse.of(user);
    }

}
