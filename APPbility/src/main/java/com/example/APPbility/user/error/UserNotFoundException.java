package com.example.APPbility.user.error;

import com.example.APPbility.error.NotFoundException;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(UUID id){
    super("No existe ningún usuario con ID: %d".formatted(id)+".");
  }

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException() {
    super("No se han encontrado usuarios.");
  }

}
