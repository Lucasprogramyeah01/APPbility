package com.example.APPbility.error.entity;

import com.example.APPbility.error.custom.NotFoundException;

public class ContinenteNotFoundException extends NotFoundException {

    public ContinenteNotFoundException(Long id) {
      super("No existe ningún continente con ID: %d".formatted(id)+".");
    }

    public ContinenteNotFoundException(String message) {
      super(message);
    }

    public ContinenteNotFoundException() {
      super("No se han encontrado continentes.");
    }

}
