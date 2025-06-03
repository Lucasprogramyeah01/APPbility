package com.example.APPbility.model;

import com.example.APPbility.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Bloque {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;

    private String descripcion;

    private LocalTime hora;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con SESION [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="sesion_id",
            foreignKey = @ForeignKey(name="fk_bloque_sesion")
    )
    private Sesion sesion;

    //Con USER [U-->B] (M-1).
    @ManyToOne
    @JoinColumn(
            name="usuarioCreador_id",
            foreignKey = @ForeignKey(name="fk_bloque_usuario")
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
        Bloque bloque = (Bloque) o;
        return getId() != null && Objects.equals(getId(), bloque.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
