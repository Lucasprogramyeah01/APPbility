package com.example.APPbility.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IntercambioPK implements Serializable {

    private static final long serialVersionUID = 1L;

        private UUID usuarioId1;

        private UUID usuarioId2;

    //EQUALS & HASHCODE ----------------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        IntercambioPK that = (IntercambioPK) o;
        return getUsuarioId1() != null && Objects.equals(getUsuarioId1(), that.getUsuarioId1())
                && getUsuarioId2() != null && Objects.equals(getUsuarioId2(), that.getUsuarioId2());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(usuarioId1, usuarioId2);
    }

}
