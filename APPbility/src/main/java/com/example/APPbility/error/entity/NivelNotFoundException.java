package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class NivelNotFoundException extends NotFoundException {

    public NivelNotFoundException(Long id) {
        super("No existe ningún nivel con ID: %d".formatted(id)+".");
    }

    public NivelNotFoundException(String message) {
        super(message);
    }

    public NivelNotFoundException() {
        super("No se han encontrado niveles.");
    }

}
