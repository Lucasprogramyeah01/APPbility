package com.example.APPbility.controller;

import com.example.APPbility.dto.intercambio.*;
import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.service.IntercambioService;
import com.example.APPbility.service.TalentoService;
import com.example.APPbility.user.dto.GetUserDTO;
import com.example.APPbility.user.model.User;
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

    @DeleteMapping("cancelar/{id}")
    public ResponseEntity<?> cancelarIntercambioPropuesto(@AuthenticationPrincipal User usuarioDemandante,
        @PathVariable Long id) {
        intercambioService.cancelarIntercambioPropuesto(id, usuarioDemandante);

        return ResponseEntity.noContent().build();
    }

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
