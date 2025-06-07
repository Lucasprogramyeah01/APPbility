package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class SesionNotFoundException extends NotFoundException {

    public SesionNotFoundException(Long id) {
      super("No existe ninguna sesión con ID: %d".formatted(id)+".");
    }

    public SesionNotFoundException(String message) {
      super(message);
    }

    public SesionNotFoundException() {
      super("No se han encontrado sesiones.");
    }

}
