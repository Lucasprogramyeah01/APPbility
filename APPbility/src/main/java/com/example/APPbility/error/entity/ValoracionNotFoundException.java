package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class ValoracionNotFoundException extends NotFoundException {

    public ValoracionNotFoundException(Long id){
        super("No existe ninguna valoración con ID: %d".formatted(id)+".");
    }

    public ValoracionNotFoundException(String message) {
        super(message);
    }

    public ValoracionNotFoundException() {
        super("No se han encontrado valoraciones.");
    }

}
