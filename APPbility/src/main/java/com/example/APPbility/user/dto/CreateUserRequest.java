package com.example.APPbility.user.dto;

import com.example.APPbility.model.Provincia;
import com.example.APPbility.model.Sexo;

import java.time.LocalDate;

public record CreateUserRequest(
        String username,
        String password,
        String email,
        String nombre,
        String apellidos,
        Sexo sexo,
        String numTelefono,
        LocalDate fechaNacimiento,
        Provincia lugarNacimiento,
        Provincia lugarResidencia
) {
}
