package com.example.APPbility.error.entity;

import com.example.APPbility.error.NotFoundException;

public class TagPRUEBANotFoundException extends NotFoundException {

    public TagPRUEBANotFoundException(Long id){
        super("No existe ningún tag con ID: %d".formatted(id)+".");
    }

    public TagPRUEBANotFoundException(String message) {
        super(message);
    }

    public TagPRUEBANotFoundException() {
        super("No se han encontrado tags.");
    }

}
