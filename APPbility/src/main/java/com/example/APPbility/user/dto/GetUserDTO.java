package com.example.APPbility.user.dto;

import com.example.APPbility.model.Provincia;
import com.example.APPbility.model.Sexo;
import com.example.APPbility.user.model.User;

import java.time.LocalDate;
import java.util.UUID;

public record GetUserDTO(
        UUID id,
        String username,
        String email,
        String nombre,
        String apellidos,
        Sexo sexo,
        String numTelefono,
        String imagenPerfil,
        LocalDate fechaNacimiento,
        Provincia lugarNacimiento,
        Provincia lugarResidencia,
        Long puntosPopularidad,
        String idiomaNativo,
        String otrosIdiomas,
        String conocimientos,
        String descripcion
) {

    public static GetUserDTO of(User u){

        String otrosIdiomas = (u.getOtrosIdiomas() != null) ? u.getOtrosIdiomas() : " ";
        String conocimientos = (u.getConocimientos() != null) ? u.getConocimientos() : " ";
        String descripcion = (u.getDescripcion() != null) ? u.getDescripcion() : " ";

        return new GetUserDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getNombre(),
                u.getApellidos(),
                u.getSexo(),
                u.getNumTelefono(),
                u.getImagenPerfil(),
                u.getFechaNacimiento(),
                u.getLugarNacimiento(),
                u.getLugarResidencia(),
                u.getPuntosPopularidad(),
                u.getIdiomaNativo(),
                otrosIdiomas,
                conocimientos,
                descripcion
        );
    }

}
