package com.example.APPbility.user.dto;

import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.dto.talento.GetTalentoDTOConNivel;
import com.example.APPbility.model.Modalidad;
import com.example.APPbility.model.Sexo;
import com.example.APPbility.user.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetUserDTOConPaisesYTalentos(
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
        String color,
        String imagenPerfil,
        String idiomaNativo,
        List<String> listaOtrosIdiomas,
        String descripcionProfesional,
        String presentacionPersonal,
        List<String> listaEnlacesExternos,
        GetPaisDTO paisNativo,
        GetPaisDTO paisResidencia,
        List<GetTalentoDTOConNivel> listaTalentos
) {

    public static GetUserDTOConPaisesYTalentos of(User u, GetPaisDTO paisNativo, GetPaisDTO paisResidencia,
        List<GetTalentoDTOConNivel> listaTalentos) {

        return new GetUserDTOConPaisesYTalentos(
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
                u.getColor(),
                u.getImagenPerfil(),
                u.getIdiomaNativo(),
                u.getListaOtrosIdiomas(),
                u.getDescripcionProfesional(),
                u.getPresentacionPersonal(),
                u.getListaEnlacesExternos(),
                paisNativo,
                paisResidencia,
                listaTalentos
        );
    }

}
