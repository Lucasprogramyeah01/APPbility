package com.example.APPbility.controller;

import com.example.APPbility.dto.talento.EditTalentoCmd;
import com.example.APPbility.dto.talento.GetTalentoDTO;
import com.example.APPbility.model.Talento;
import com.example.APPbility.service.TalentoService;
import com.example.APPbility.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/talento/")
@Tag(name = "Talento", description = "Controlador de Talento, para poder realizar sus operaciones de gestión.")
public class TalentoController {

    private final TalentoService talentoService;

    @PostMapping
    public ResponseEntity<Talento> save(@AuthenticationPrincipal User user,
        @Valid @RequestPart("editTalentoCmd") EditTalentoCmd nuevo,
        @RequestPart("listaImagenes")MultipartFile... listaMultipartFile){
        return ResponseEntity.status(HttpStatus.CREATED).body(talentoService.save(user, nuevo, listaMultipartFile));
    }

    @PutMapping("{id}")
    public GetTalentoDTO edit(@AuthenticationPrincipal User user, @PathVariable Long id,
        @Valid @RequestPart("editTalentoCmd") EditTalentoCmd editTalentoCmd,
        @RequestPart("listaImagenes")MultipartFile... listaMultipartFile){
        return GetTalentoDTO.of(talentoService.edit(editTalentoCmd, id, listaMultipartFile));
    }

}
