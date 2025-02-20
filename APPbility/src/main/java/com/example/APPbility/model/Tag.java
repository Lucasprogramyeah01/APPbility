package com.example.APPbility.model;

import com.example.APPbility.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con USER (MT - MU).
    @ManyToMany(mappedBy = "listaTags", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private Set<User> listaUsuarios = new HashSet<>();

    //MÉTODOS HELPER

        //Con USER:

        public void addUsuario(User u){
            this.listaUsuarios.add(u);
            u.getListaTags().add(this);
        }

        public void removeUsuario(User u){
            u.getListaTags().remove(this);
            this.listaUsuarios.remove(u);
        }

    //EQUALS & HASHCODE ----------------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Tag tag = (Tag) o;
        return getId() != null && Objects.equals(getId(), tag.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
