package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class PaisNotFoundException extends NotFoundException {

    public PaisNotFoundException(Long id) {
        super("No existe ningún país con ID: %d".formatted(id)+".");
    }

    public PaisNotFoundException(String message) {
        super(message);
    }

    public PaisNotFoundException() {
        super("No se han encontrado países.");
    }

}
