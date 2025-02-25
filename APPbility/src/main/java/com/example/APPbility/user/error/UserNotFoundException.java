package com.example.APPbility.user.error;

import com.example.APPbility.error.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(Long id){
    super("No existe ningún usuario con ID: %d".formatted(id)+".");
  }

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException() {
    super("No se han encontrado usuarios.");
  }

}
