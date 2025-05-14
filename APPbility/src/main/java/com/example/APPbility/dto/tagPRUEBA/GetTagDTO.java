package com.example.APPbility.dto.tagPRUEBA;

import com.example.APPbility.model.TagPRUEBA;

public record GetTagDTO(
        Long id,
        String nombre
){

    public static GetTagDTO of(TagPRUEBA t){
        return new GetTagDTO(
                t.getId(),
                t.getNombre()
        );
    }

}
