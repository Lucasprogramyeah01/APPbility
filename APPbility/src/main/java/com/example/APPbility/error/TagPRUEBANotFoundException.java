package com.example.APPbility.error;

public class TagPRUEBANotFoundException extends NotFoundException {

    public TagPRUEBANotFoundException(Long id){
        super("No existe ningún tag con ID: %d".formatted(id)+".");
    }

    public TagPRUEBANotFoundException(String message) {
        super(message);
    }

    public TagPRUEBANotFoundException() {
        super("No se han encontrado tags.");
    }

}
