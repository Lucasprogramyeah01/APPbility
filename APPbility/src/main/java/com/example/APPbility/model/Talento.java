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
public class Talento {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;

    private String descripcion;

    private String imagen;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con NIVEL [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="nivel_id",
            foreignKey = @ForeignKey(name="fk_talento_nivel")
    )
    private Nivel nivel;

    //Con USER [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="user_id",
            foreignKey = @ForeignKey(name="fk_talento_user")
    )
    private User usuario;

    //MÉTODOS HELPER --------------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Talento talento = (Talento) o;
        return getId() != null && Objects.equals(getId(), talento.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
