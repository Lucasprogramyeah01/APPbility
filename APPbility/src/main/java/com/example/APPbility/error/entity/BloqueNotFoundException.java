package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class BloqueNotFoundException extends NotFoundException {

    public BloqueNotFoundException(Long id) {
        super("No existe ningún bloque con ID: %d".formatted(id)+".");
    }

    public BloqueNotFoundException(String message) {
        super(message);
    }

    public BloqueNotFoundException() {
        super("No se han encontrado bloques.");
    }

}
