package com.example.APPbility.controller;

import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.service.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag/")
@Tag(name = "Tag", description = "Controlador de Tag, para poder realizar sus operaciones de gestión.")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<GetTagDTO> findAll(){
        return tagService.findAll();
    }

}
