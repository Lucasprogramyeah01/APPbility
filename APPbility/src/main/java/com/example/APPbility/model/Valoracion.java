package com.example.APPbility.model;

import com.example.APPbility.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Valoracion {

    @Id
    @GeneratedValue
    private Long id;

    private int valoracion;

    private String titulo;

    private String resenha;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con USER -> Usuario escritor (MT - 1U).
    @ManyToOne
    @JoinColumn(
            name="usuario_escritor_id",
            foreignKey = @ForeignKey(name="fk_valoracion_usuarioEscritor")
    )
    private User usuarioEscritor;

    //Con USER -> Usuario valorado (MT - 1U).
    @ManyToOne
    @JoinColumn(
            name="usuario_valorado_id",
            foreignKey = @ForeignKey(name="fk_valoracion_usuarioValorado")
    )
    private User usuarioValorado;

    //EQUALS & HASHCODE ----------------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Valoracion that = (Valoracion) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
