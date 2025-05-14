package com.example.APPbility.error;

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
