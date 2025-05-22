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
public class Nivel {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String color;

    private int orden;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con TALENTO [<-->] (1-M).
    @OneToMany(mappedBy = "nivel", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Talento> listaTalentos = new ArrayList<>();

    //MÉTODOS HELPER --------------------------------------------------------------------------------

    //Con TALENTO:
    public void addTalento(Talento t){
        t.setNivel(this);
        this.getListaTalentos().add(t);
    }
    public void removeTalento(Talento t){
        this.getListaTalentos().remove(t);
        t.setNivel(null);
    }

    //EQUALS & HASHCODE -----------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Nivel nivel = (Nivel) o;
        return getId() != null && Objects.equals(getId(), nivel.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
