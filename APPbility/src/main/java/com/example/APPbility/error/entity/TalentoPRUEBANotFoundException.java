package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class TalentoPRUEBANotFoundException extends NotFoundException {

    public TalentoPRUEBANotFoundException(Long id){
        super("No existe ningún talento con ID: %d".formatted(id)+".");
    }

    public TalentoPRUEBANotFoundException(String message) {
        super(message);
    }

    public TalentoPRUEBANotFoundException() {
        super("No se han encontrado talentos.");
    }

}
