package com.example.APPbility.error;

public class TalentoPRUEBANotFoundException extends NotFoundException {

    public TalentoPRUEBANotFoundException(Long id){
        super("No existe ningún talento con ID: %d".formatted(id)+".");
    }

    public TalentoPRUEBANotFoundException(String message) {
        super(message);
    }

    public TalentoPRUEBANotFoundException() {
        super("No se han encontrado talentos.");
    }

}
