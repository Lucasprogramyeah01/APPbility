package com.example.APPbility.controller;

import com.example.APPbility.dto.tag.EditTagCmd;
import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.dto.tag.GetTagDTOCompleto;
import com.example.APPbility.service.TagService;
import com.example.APPbility.user.dto.GetUserDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag/")
@Tag(name = "Tag", description = "Controlador de Tag, para poder realizar sus operaciones de gestión.")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public Page<GetTagDTO> findAll(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable){
        return tagService.findAll(pageable);
    }

    @GetMapping("{id}")
    public GetTagDTOCompleto findByID(@PathVariable Long id){
        Set<GetUserDTO> listaUsuarios = tagService.getListaUsuariosByTagID(id);

        com.example.APPbility.model.Tag t = tagService.findById(id);

        return GetTagDTOCompleto.of(t, listaUsuarios);
    }

    @PostMapping
    public ResponseEntity<com.example.APPbility.model.Tag> save(@Valid @RequestBody EditTagCmd nuevo){
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.save(nuevo));
    }

    @PutMapping("{id}")
    public GetTagDTO edit(@RequestBody EditTagCmd editTagCmd, @PathVariable Long id){
        return GetTagDTO.of(tagService.edit(editTagCmd, id));
    }

}
