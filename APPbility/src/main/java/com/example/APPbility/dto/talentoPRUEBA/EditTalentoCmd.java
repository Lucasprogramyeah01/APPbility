package com.example.APPbility.dto.talentoPRUEBA;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EditTalentoCmd(
        @NotBlank(message = "{editTalentoCmd.titulo.notblank}")
        @NotNull(message = "{editTalentoCmd.titulo.notnull}")
        String titulo,
        @NotNull(message = "{editTalentoCmd.descripcion.notnull}")
        String descripcion,
        List<String> listaImagenes
) {
}
