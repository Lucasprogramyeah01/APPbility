package com.example.APPbility.error;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(Long id){
        super("No existe ningún tag con ID: %d".formatted(id)+".");
    }

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException() {
        super("No se han encontrado tags.");
    }

}
