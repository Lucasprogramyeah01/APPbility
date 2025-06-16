package com.example.APPbility.model;

import com.example.APPbility.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MensajeChat {

    @Id
    @GeneratedValue
    private Long id;

    private String contenido;

    private LocalDateTime fechaEnvio;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con INTERCAMBIO [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="intercambio_id_asociado_a_mensaje",
            foreignKey = @ForeignKey(name="fk_mensajeChat_intercambio")
    )
    private Intercambio intercambio;

    //Con USER [U-->MC] (M-1).
    @ManyToOne
    @JoinColumn(
            name="usuarioAutor_id",
            foreignKey = @ForeignKey(name="fk_mensajeChat_usuario")
    )
    private User usuario;

    //EQUALS & HASHCODE -----------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MensajeChat that = (MensajeChat) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
