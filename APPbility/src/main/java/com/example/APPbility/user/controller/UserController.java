package com.example.APPbility.user.controller;

import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.security.jwt.access.JwtService;
import com.example.APPbility.security.jwt.refresh.RefreshToken;
import com.example.APPbility.security.jwt.refresh.RefreshTokenRequest;
import com.example.APPbility.security.jwt.refresh.RefreshTokenService;
import com.example.APPbility.service.ValoracionService;
import com.example.APPbility.user.dto.EditUserCMD;
import com.example.APPbility.user.dto.GetUserDTOConPaisesYTalentos;
import com.example.APPbility.user.dto.GetUserDTOConPaises;
//import com.example.APPbility.user.dto.GetUserDTOCompleto;
import com.example.APPbility.user.dto.seguridad.ActivateAccountRequest;
import com.example.APPbility.user.dto.seguridad.CreateUserRequest;
import com.example.APPbility.user.dto.seguridad.LoginRequest;
import com.example.APPbility.user.dto.seguridad.UserResponse;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.service.UserService;
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
    private final ValoracionService valoracionService;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    //ENDPOINTS DEL CONTROLADOR --------------------------------------------------------------------------------

    @GetMapping
    public Page<GetUserDTOConPaises> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return userService.findAll(pageable)
        .map(user -> {
            GetPaisDTO paisNativoDTO = GetPaisDTO.of(user.getPaisNativo());
            GetPaisDTO paisResidenciaDTO = GetPaisDTO.of(user.getPaisResidencia());
            return GetUserDTOConPaises.of(user, paisNativoDTO, paisResidenciaDTO);
        });
    }

    @GetMapping("{id}")
    public GetUserDTOConPaises findByID(@PathVariable UUID id){
        User user = userService.findById(id);

        return GetUserDTOConPaises.of(
            user,
            GetPaisDTO.of(user.getPaisNativo()),
            GetPaisDTO.of(user.getPaisResidencia())
        );
    }

    @PutMapping("/editar")
    public ResponseEntity<UserResponse> edit(@AuthenticationPrincipal User usuarioAutenticado,
        @Valid @RequestPart("usuario") EditUserCMD editUserCMD,
        @RequestPart(value = "imagenPerfil", required = false) MultipartFile multipartFile) {
        User usuarioActualizado = userService.edit(usuarioAutenticado.getId(), editUserCMD, multipartFile);

        return ResponseEntity.ok(UserResponse.of(usuarioActualizado));
    }

    @PostMapping("marcar/{favoritoID}/favorito")
    public ResponseEntity<String> marcarUsuarioComoFavorito(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable UUID favoritoID) {
        userService.marcarUsuarioComoFavorito(usuarioAutenticado.getId(), favoritoID);

        return ResponseEntity.ok("Usuario añadido a favoritos correctamente.");
    }

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

    @PostMapping("desmarcar/{favoritoID}/favorito")
    public ResponseEntity<String> desmarcarUsuarioDeFavoritos(@AuthenticationPrincipal User usuarioAutenticado,
        @PathVariable UUID favoritoID) {
        userService.desmarcarUsuarioDeFavoritos(usuarioAutenticado.getId(), favoritoID);

        return ResponseEntity.ok("Usuario eliminado de favoritos correctamente.");
    }

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

    @GetMapping("{usuarioID}/calcular-media")
    public ResponseEntity<Double> calcularMediaDePuntuacionesDeUsuario(@PathVariable UUID usuarioID) {
        Double media = userService.calcularMediaDePuntuacionesDeUsuario(usuarioID);
        return ResponseEntity.ok(media);
    }

    //ENDPOINTS RELACIONADOS CON SEGURIDAD ---------------------------------------------------------------------

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestPart("usuario") CreateUserRequest createUserRequest,
        @RequestPart(value = "imagenPerfil", required = false) MultipartFile multipartFile) {
        User user = userService.createUser(createUserRequest, multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(user));
    }

    @PostMapping("/auth/login")
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

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal User usuarioAutenticado) {
        refreshTokenService.deleteAllByUser(usuarioAutenticado);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Sesión cerrada correctamente.");
    }

    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED).body(refreshTokenService.refreshToken(token));
    }

    @PostMapping("/activate/account/")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequest req) {
        String token = req.token();

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(UserResponse.of(userService.activateAccount(token)));
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal User user) {
        return UserResponse.of(user);
    }

    @GetMapping("/me/admin")
    public User adminMe(@AuthenticationPrincipal User user) {
        return user;
    }

}
