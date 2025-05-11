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
public class Pais {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String codigoISO;

    private String bandera;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con CONTINENTE [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="continente_id",
            foreignKey = @ForeignKey(name="fk_pais_continente")
    )
    private Continente continente;

    //Con USER [<-->] (1-M).
    @OneToMany(mappedBy = "paisNativo", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<User> listaUsuariosNativos = new ArrayList<>();
    //Con USER [<-->] (1-M).
    @OneToMany(mappedBy = "paisResidencia", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<User> listaUsuariosResidentes = new ArrayList<>();

    //MÉTODOS HELPER --------------------------------------------------------------------------------

    //Con USER:
    public void addUsuarioNativo(User u){
        u.setPaisNativo(this);
        this.getListaUsuariosNativos().add(u);
    }
    public void removeUsuarioNativo(User u){
        this.getListaUsuariosNativos().remove(u);
        u.setPaisNativo(null);
    }
    //Con USER:
    public void addUsuarioResidente(User u){
        u.setPaisResidencia(this);
        this.getListaUsuariosResidentes().add(u);
    }
    public void removeUsuarioResidente(User u){
        this.getListaUsuariosResidentes().remove(u);
        u.setPaisResidencia(null);
    }

    //EQUALS & HASHCODE -----------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Pais pais = (Pais) o;
        return getId() != null && Objects.equals(getId(), pais.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
