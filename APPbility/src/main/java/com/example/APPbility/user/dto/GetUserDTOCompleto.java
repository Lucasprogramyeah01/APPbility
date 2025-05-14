package com.example.APPbility.user.dto;

import com.example.APPbility.dto.tagPRUEBA.GetTagDTO;
import com.example.APPbility.dto.talentoPRUEBA.GetTalentoDTO;
import com.example.APPbility.dto.valoracion.GetValoracionDTO;
import com.example.APPbility.model.Sexo;
import com.example.APPbility.user.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/*public record GetUserDTOCompleto(
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
        String descripcion,
        Set<GetTagDTO> listaTags,
        List<GetTalentoDTO> listaTalentos,
        List<GetValoracionDTO> listaValoracionesRealizadas,
        List<GetValoracionDTO> listaValoracionesRecibidas,
        Set<GetUserDTO> listaUsuariosFavoritos,
        Set<GetUserDTO> listaUsuariosSeguidores
) {

    public static GetUserDTOCompleto of(User u, Set<GetTagDTO> listaTags, List<GetTalentoDTO> listaTalentos,
        List<GetValoracionDTO> listaValoracionesRealizadas, List<GetValoracionDTO> listaValoracionesRecibidas,
        Set<GetUserDTO> listaUsuariosFavoritos, Set<GetUserDTO> listaUsuariosSeguidores) {

        String otrosIdiomas = (u.getOtrosIdiomas() != null) ? u.getOtrosIdiomas() : " ";
        String conocimientos = (u.getConocimientos() != null) ? u.getConocimientos() : " ";
        String descripcion = (u.getDescripcion() != null) ? u.getDescripcion() : " ";

        return new GetUserDTOCompleto(
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
                descripcion,
                listaTags,
                listaTalentos,
                listaValoracionesRealizadas,
                listaValoracionesRecibidas,
                listaUsuariosFavoritos,
                listaUsuariosSeguidores
        );
    }

}*/
