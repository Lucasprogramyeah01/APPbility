package com.example.APPbility.model;

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
public class Continente {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con PAIS [<-->] (1-M).
    @OneToMany(mappedBy = "continente", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Pais> listaPaises = new ArrayList<>();

    //MÉTODOS HELPER --------------------------------------------------------------------------------

    //Con PAIS:
    public void addPais(Pais p){
        p.setContinente(this);
        this.getListaPaises().add(p);
    }
    public void removePais(Pais p){
        this.getListaPaises().remove(p);
        p.setContinente(null);
    }

    //EQUALS & HASHCODE -----------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Continente that = (Continente) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
