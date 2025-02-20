package com.example.APPbility.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Intercambio {

    @EmbeddedId
    private IntercambioPK intercambioPK = new IntercambioPK();

    private String talento1;

    private String talento2;

    private String descripcion;

    private boolean finalizado = false;

    private LocalDate fechaInicio = LocalDate.now();

    private LocalDate fechaFin;

    //EQUALS & HASHCODE ----------------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Intercambio that = (Intercambio) o;
        return getIntercambioPK() != null && Objects.equals(getIntercambioPK(), that.getIntercambioPK());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(intercambioPK);
    }

}
