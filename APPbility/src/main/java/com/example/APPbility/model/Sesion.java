package com.example.APPbility.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
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
public class Sesion {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate fecha;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con INTERCAMBIO [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="intercambio_id_asociado_a_sesion",
            foreignKey = @ForeignKey(name="fk_sesion_intercambio")
    )
    private Intercambio intercambio;

    //Con BLOQUE [<-->] (1-M).
    @OneToMany(mappedBy = "sesion", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Bloque> listaBloques = new ArrayList<>();

    //MÉTODOS HELPER --------------------------------------------------------------------------------

    //Con BLOQUE:
    public void addBloque(Bloque b){
        b.setSesion(this);
        this.getListaBloques().add(b);
    }
    public void removeBloque(Bloque b){
        this.getListaBloques().remove(b);
        b.setSesion(null);
    }

    //EQUALS & HASHCODE -----------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Sesion sesion = (Sesion) o;
        return getId() != null && Objects.equals(getId(), sesion.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
