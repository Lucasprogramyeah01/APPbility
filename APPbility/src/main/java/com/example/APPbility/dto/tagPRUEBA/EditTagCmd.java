package com.example.APPbility.dto.tagPRUEBA;

import com.example.APPbility.validation.tagPRUEBA.UniqueNameTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditTagCmd(
        @NotBlank(message = "{editTagCmd.nombre.notblank}")
        @NotNull(message = "{editTagCmd.nombre.notnull}")
        @UniqueNameTag
        String nombre
) {
}
