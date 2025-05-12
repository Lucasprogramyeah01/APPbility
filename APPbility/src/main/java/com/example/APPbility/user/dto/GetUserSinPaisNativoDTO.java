package com.example.APPbility.user.dto;

import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.model.Modalidad;
import com.example.APPbility.model.Sexo;
import com.example.APPbility.user.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetUserSinPaisNativoDTO(
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
        List<String> listaEnlacesExternos
        //GetPaisDTO paisResidencia
) {

    public static GetUserSinPaisNativoDTO of(User u){
        return new GetUserSinPaisNativoDTO(
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
                u.getListaOtrosIdiomas(),
                u.getDescripcionProfesional(),
                u.getPresentacionPersonal(),
                u.getListaEnlacesExternos()
        );
    }

}
