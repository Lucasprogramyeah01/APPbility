package com.example.APPbility.model;

import com.example.APPbility.user.model.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class IntercambioPK implements Serializable {

    private UUID usuarioDemandanteID;
    private UUID usuarioSolicitadoID;
    private LocalDateTime fechaSolicitud;

}
