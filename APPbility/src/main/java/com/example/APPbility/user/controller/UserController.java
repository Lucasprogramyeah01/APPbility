package com.example.APPbility.user.controller;

import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.security.jwt.access.JwtService;
import com.example.APPbility.security.jwt.refresh.RefreshToken;
import com.example.APPbility.security.jwt.refresh.RefreshTokenRequest;
import com.example.APPbility.security.jwt.refresh.RefreshTokenService;
import com.example.APPbility.user.dto.GetUserDTOConPaises;
//import com.example.APPbility.user.dto.GetUserDTOCompleto;
import com.example.APPbility.user.dto.seguridad.ActivateAccountRequest;
import com.example.APPbility.user.dto.seguridad.LoginRequest;
import com.example.APPbility.user.dto.seguridad.UserResponse;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public Page<GetUserDTOConPaises> findAll(@PageableDefault/*(sort = "nombre", direction = Sort.Direction.ASC)*/ Pageable pageable){
        /*GetPaisDTO getPaisNativoDTO = GetPaisDTO.of(userService.getPaisNativoByUsuarioID());
        GetPaisDTO getPaisResidenciaDTO = GetPaisDTO.of(userService.getPaisResidenciaByUsuarioID());*/

        return userService.findAll(pageable)//.map(GetUserDTO::of);
        .map(user -> {
            GetPaisDTO paisNativoDTO = GetPaisDTO.of(user.getPaisNativo());
            GetPaisDTO paisResidenciaDTO = GetPaisDTO.of(user.getPaisResidencia());
            return GetUserDTOConPaises.of(user, paisNativoDTO, paisResidenciaDTO);
        });
    }

    /*@GetMapping("/user/{id}")
    public GetUserDTOCompleto findByID(@PathVariable UUID id){
        Set<GetTagDTO> listaTags = userService.getListaTagsByUsuarioID(id);
        List<GetTalentoDTO> listaTalentos = userService.getListaTalentosByUsuarioID(id);
        List<GetValoracionDTO> listaValoracionesRealizadas = userService.getListaValoracionesRealizadasByUsuarioID(id);
        List<GetValoracionDTO> listaValoracionesRecibidas = userService.getListaValoracionesRecibidasByUsuarioID(id);
        Set<GetUserDTO> listaUsuariosFavoritos = userService.getListaUsuariosFavoritosByUsuarioID(id);
        Set<GetUserDTO> listaUsuariosSeguidores = userService.getListaUsuariosSeguidoresByUsuarioID(id);

        User u = userService.findById(id);

        return GetUserDTOCompleto.of(u, listaTags, listaTalentos, listaValoracionesRealizadas, listaValoracionesRecibidas,
            listaUsuariosFavoritos, listaUsuariosSeguidores);
    }*/

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


    //ENDPOINTS RELACIONADOS CON SEGURIDAD ---------------------------------------------------------------------

    /*@PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(user));
    }*/

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
