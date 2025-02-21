package com.example.APPbility.dto.tag;

import com.example.APPbility.model.Tag;

public record GetTagDTO(
        Long id,
        String nombre
){

    public static GetTagDTO of(Tag t){
        return new GetTagDTO(
                t.getId(),
                t.getNombre()
        );
    }

}
