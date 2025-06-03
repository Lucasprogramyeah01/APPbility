package com.example.APPbility.controller;

import com.example.APPbility.dto.nivel.GetNivelDTO;
import com.example.APPbility.dto.talento.CreateTalentoCMD;
import com.example.APPbility.dto.talento.GetTalentoDTOCompleto;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Talento;
import com.example.APPbility.service.NivelService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/talento/")
@Tag(name = "Talento", description = "Controlador de Talento, para poder realizar sus operaciones de gestión.")
public class TalentoController {

    private final TalentoService talentoService;
    private final NivelService nivelService;

    @GetMapping("{id}")
    public Page<GetTalentoDTOConNivel> findTalentosfromUsuario(@PageableDefault(sort = "titulo",
        direction = Sort.Direction.ASC) Pageable pageable, @PathVariable UUID id) {
        Page<Talento> listaTalentos = talentoService.findTalentosfromUsuario(id, pageable);

        List<GetTalentoDTOConNivel> listaTalentosConNivel = listaTalentos.stream()
                .map(t -> GetTalentoDTOConNivel.of(t, GetNivelDTO.of(talentoService.getNivelByTalentoID(t.getId()))))
                .toList();

        return new PageImpl<>(listaTalentosConNivel, pageable, listaTalentos.getTotalElements());
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GetTalentoDTOCompleto> save(@AuthenticationPrincipal User user, @Valid
        @RequestPart("talento") CreateTalentoCMD nuevo, @RequestPart(value = "imagen", required = false)
        MultipartFile multipartFile){
        Talento nuevoTalento = talentoService.save(nuevo, multipartFile, user);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(GetTalentoDTOCompleto.of(nuevoTalento,
                GetNivelDTO.of(talentoService.getNivelByTalentoID(nuevoTalento.getId())), GetUserDTO.of(user)));
    }

    @PutMapping(value = "{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public GetTalentoDTOCompleto edit(@AuthenticationPrincipal User user, @Valid @RequestPart("talento") CreateTalentoCMD editTalentoCMD,
        @RequestPart(value = "imagen", required = false) MultipartFile multipartFile, @PathVariable Long id){
        Talento talentoEditado = talentoService.edit(editTalentoCMD, multipartFile, user, id);

        return GetTalentoDTOCompleto.of(talentoEditado,
            GetNivelDTO.of(talentoService.getNivelByTalentoID(talentoEditado.getId())), GetUserDTO.of(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable Long id){
        talentoService.delete(id, user);

        return ResponseEntity.noContent().build();
    }

}