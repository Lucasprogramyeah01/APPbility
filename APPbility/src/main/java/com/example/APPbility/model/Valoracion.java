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

    private int puntuacion;

    private String titulo;

    private String resenha;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con USER [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="usuario_escritor_id",
            foreignKey = @ForeignKey(name="fk_valoracion_usuarioEscritor")
    )
    private User usuarioEscritor;
    //Con USER [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="usuario_valorado_id",
            foreignKey = @ForeignKey(name="fk_valoracion_usuarioValorado")
    )
    private User usuarioValorado;

    //Con INTERCAMBIO [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="intercambio_id_asociado_a_valoracion",
            foreignKey = @ForeignKey(name="fk_valoracion_intercambio")
    )
    private Intercambio intercambio;

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
