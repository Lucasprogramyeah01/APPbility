package com.example.APPbility.user.dto;

import com.example.APPbility.model.Modalidad;
import com.example.APPbility.model.Pais;
import com.example.APPbility.model.Sexo;
import com.example.APPbility.user.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetUserDTO(
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

    public static GetUserDTO of(User u){

        /*String otrosIdiomas = (u.getOtrosIdiomas() != null) ? u.getOtrosIdiomas() : " ";
        String conocimientos = (u.getConocimientos() != null) ? u.getConocimientos() : " ";
        String descripcion = (u.getDescripcion() != null) ? u.getDescripcion() : " ";*/

        return new GetUserDTO(
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
                u.getListaEnlacesExternos(),
                u.getPaisNativo(),
                u.getPaisResidencia()
                /*u.getId(),
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
                descripcion*/
        );
    }

}
