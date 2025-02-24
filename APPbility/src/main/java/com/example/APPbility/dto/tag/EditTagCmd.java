package com.example.APPbility.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditTagCmd(
        @NotBlank
        @NotNull
        String nombre
) {
}
