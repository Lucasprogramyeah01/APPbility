package com.example.APPbility.user.dto;

import com.example.APPbility.model.Modalidad;
import com.example.APPbility.model.Sexo;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record EditUserCMD(
        @NotBlank(message = "{user.nombre.notBlank}")
        String nombre,

        @NotBlank(message = "{user.apellidos.notBlank}")
        String apellidos,

        @Past(message = "{user.fechaNacimiento.past}")
        @NotNull(message = "{user.fechaNacimiento.notNull}")
        LocalDate fechaNacimiento,

        @NotNull(message = "{user.sexo.notNull}")
        Sexo sexo,

        @NotNull(message = "{user.modalidadPreferida.notNull}")
        Modalidad modalidadPreferida,

        @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "{user.numTelefono.pattern}")
        @NotBlank(message = "{user.numTelefono.notBlank}")
        String numTelefono,

        @NotNull(message = "{user.mostrarNumTelefono.notNull}")
        boolean mostrarNumTelefono,

        String color,

        String imagenPerfil,

        @NotNull(message = "{user.idiomaNativo.notNull}")
        String idiomaNativo,

        List<String> listaOtrosIdiomas,

        @NotBlank(message = "{user.descripcionProfesional.notBlank}")
        String descripcionProfesional,

        @NotBlank(message = "{user.presentacionPersonal.notBlank}")
        String presentacionPersonal,

        List<String> listaEnlacesExternos,

        @NotNull(message = "{user.paisNativo.notNull}")
        Long paisNativoID,

        @NotNull(message = "{user.paisResidencia.notNull}")
        Long paisResidenciaID
) {
}
