package com.example.APPbility.user.dto.seguridad;

import com.example.APPbility.model.Modalidad;
import com.example.APPbility.model.Pais;
import com.example.APPbility.model.Sexo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateUserRequest(
        UUID id,
        String username,
        String password,
        String email,
        String nombre,
        String apellidos,
        LocalDate fechaNacimiento,
        Sexo sexo,
        Modalidad modalidadPreferida,
        String numTelefono,
        boolean mostrarNumTelefono,
        String imagenPerfil,
        String idiomaNativo,
        List<String> listaOtrosIdiomas,
        String descripcionProfesional,
        String presentacionPersonal,
        List<String> listaEnlacesExternos,
        Pais paisNativo,
        Pais paisResidencia
) {
}
