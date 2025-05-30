package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class TalentoNotFoundException extends NotFoundException {

    public TalentoNotFoundException(Long id){
        super("No existe ningún talento con ID: %d".formatted(id)+".");
    }

    public TalentoNotFoundException(String message) {
        super(message);
    }

    public TalentoNotFoundException() {
        super("No se han encontrado talentos.");
    }

}
