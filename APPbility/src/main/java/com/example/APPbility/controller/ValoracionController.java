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
