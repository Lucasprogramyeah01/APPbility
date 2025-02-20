package com.example.APPbility.model;

import com.example.APPbility.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
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

    @Lob
    private String descripcion;

    @ElementCollection
    private List<String> listaImagenes = new ArrayList<>();

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con USER (MT - 1U).
    @ManyToOne
    @JoinColumn(
            name="talento_id",
            foreignKey = @ForeignKey(name="fk_talento_usuario")
    )
    private User usuario;

    //EQUALS & HASHCODE ----------------------------------------------------------------------------------

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
