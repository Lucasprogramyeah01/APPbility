package com.example.APPbility.user.dto;

import com.example.APPbility.model.Modalidad;
import com.example.APPbility.model.Sexo;
import com.example.APPbility.user.model.User;

import java.time.LocalDate;
import java.util.UUID;

public record GetUserDTOSinListas(
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
        String descripcionProfesional,
        String presentacionPersonal
) {

    public static GetUserDTOSinListas of(User u){
        return new GetUserDTOSinListas(
                u.getId(),
                u.getUsername(),
                u.getPassword(),
                u.getEmail(),
                u.getNombre(),
                u.getApellidos(),
                u.getFechaNacimiento(),
                u.getSexo(),
                u.getModalidadPreferida(),
                u.getNumTelefono(),
                u.isMostrarNumTelefono(),
                u.getImagenPerfil(),
                u.getIdiomaNativo(),
                u.getDescripcionProfesional(),
                u.getPresentacionPersonal()
        );
    }

}
