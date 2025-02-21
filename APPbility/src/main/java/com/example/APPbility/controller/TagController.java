package com.example.APPbility.controller;

import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag/")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<GetTagDTO> findAll(){
        return tagService.findAll();
    }

}
