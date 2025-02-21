package com.example.APPbility.user.model;

import com.example.APPbility.model.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_entity")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //-----

    @Column(unique = true, updatable = false)
    private String username;

    private String password;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @Builder.Default
    private boolean enabled = false;

    //-----

    private String nombre;

    private String apellidos;

    private Sexo sexo;

    private String imagenPerfil;

    private LocalDate fechaNacimiento;

    private Provincia lugarNacimiento;

    private Provincia lugarResidencia;

    private Long puntosPopularidad;

    private String idiomas;

    private String conocimientos;

    @Lob
    private String descripcion;

    //-----

    private String activationToken;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }

    //-----

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con TAG (MU - MT).
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "se_etiqueta_con",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            foreignKey = @ForeignKey(name = "fk_usuario_tag"),
            inverseForeignKey = @ForeignKey(name = "fk_tag_usuario")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Tag> listaTags = new HashSet<>();

    //Con TALENTO (1U - MT).
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Talento> listaTalentos = new ArrayList<>();

    //Con VALORACION -> Para Valoraciones realizadas (1U - MV).
    @OneToMany(mappedBy = "usuarioEscritor", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Valoracion> listaValoracionesRealizadas = new ArrayList<>();

    //Con VALORACION -> Para Valoraciones recibidas (1U - MV).
    @OneToMany(mappedBy = "usuarioValorado", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Valoracion> listaValoracionesRecibidas = new ArrayList<>();

    //Con USER -> Para Usuarios favoritos (MU - MU).
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "marca_como_favorito",
            joinColumns = @JoinColumn(name = "usuarioId_que_marca_usuarioFavoritoId"),
            inverseJoinColumns = @JoinColumn(name = "usuarioFavoritoId_relacion_usuarioId"),
            foreignKey = @ForeignKey(name = "fk_usuario_usuarioFavorito"),
            inverseForeignKey = @ForeignKey(name = "fk_usuarioFavorito_usuario")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<User> listaUsuariosFavoritos = new HashSet<>();

    //Con USER -> Para Usuarios seguidores (MT - MU).
    @ManyToMany(mappedBy = "listaUsuariosFavoritos", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private Set<User> listaUsuariosSeguidores = new HashSet<>();

    //MÉTODOS HELPER

        //Con TAG:

        public void addTag(Tag t){
            this.listaTags.add(t);
            t.getListaUsuarios().add(this);
        }

        public void removeTag(Tag t){
            t.getListaUsuarios().remove(this);
            this.listaTags.remove(t);
        }

        //Con TALENTO:

        public void addTalento(Talento t){
            t.setUsuario(this);
            this.getListaTalentos().add(t);
        }

        public void removeTalento(Talento t){
            this.getListaTalentos().remove(t);
            t.setUsuario(null);
        }

        //Con VALORACION -> Para Valoraciones realizadas:

        public void addValoracionRealizada(Valoracion v){
            v.setUsuarioEscritor(this);
            this.getListaValoracionesRealizadas().add(v);
        }

        public void removeValoracionRealizada(Valoracion v){
            this.getListaValoracionesRealizadas().remove(v);
            v.setUsuarioEscritor(null);
        }

        //Con VALORACION -> Para Valoraciones recibidas:

        public void addValoracionRecibida(Valoracion v){
            v.setUsuarioValorado(this);
            this.getListaValoracionesRecibidas().add(v);
        }

        public void removeValoracionRecibida(Valoracion v){
            this.getListaValoracionesRecibidas().remove(v);
            v.setUsuarioValorado(null);
        }

        //Con USER -> Para Usuarios favoritos;

        public void addUsuarioFavorito(User u){
            this.listaUsuariosFavoritos.add(u);
            u.getListaUsuariosSeguidores().add(this);
        }

        public void removeUsuarioFavorito(User u){
            u.getListaUsuariosSeguidores().remove(this);
            this.listaUsuariosFavoritos.remove(u);
        }

        //Con USER -> Para Usuarios seguidores;

        public void addUsuarioSeguidor(User u){
            this.listaUsuariosSeguidores.add(u);
            u.getListaUsuariosFavoritos().add(this);
        }

        public void removeUsuarioSeguidor(User u){
            u.getListaUsuariosFavoritos().remove(this);
            this.listaUsuariosSeguidores.remove(u);
        }

    //EQUALS & HASHCODE ----------------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
