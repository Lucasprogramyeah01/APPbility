package com.example.APPbility.error.custom;

public class EntityWithRelationshipsException extends RuntimeException {

    public EntityWithRelationshipsException(String message) {
        super(message);
    }

    public EntityWithRelationshipsException(String nombreEntidad, Long id) {
        super("No se puede eliminar " + nombreEntidad + " con ID: " + id + " porque tiene dependencias asociadas.");
    }

}
