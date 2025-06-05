package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class IntercambioNotFoundException extends NotFoundException {

    public IntercambioNotFoundException(Long id){
        super("No existe ningún intercambio con ID: %d".formatted(id)+".");
    }

    public IntercambioNotFoundException(String message) {
        super(message);
    }

    public IntercambioNotFoundException() {
        super("No se han encontrado intercambios.");
    }

}
