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

    @Column(unique = true, updatable = false)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private Modalidad modalidadPreferida;

    private String numTelefono;
    private boolean mostrarNumTelefono;
    private String color;
    private String imagenPerfil;
    private String idiomaNativo;

    @ElementCollection
    private List<String> listaOtrosIdiomas;

    private String descripcionProfesional;
    private String presentacionPersonal;

    @ElementCollection
    private List<String> listaEnlacesExternos;

    //---------------------

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @Builder.Default
    private boolean enabled = false;

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

    //---------------------

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con PAIS [<-nace_en->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="paisNativo_id",
            foreignKey = @ForeignKey(name="fk_user_paisNativo")
    )
    private Pais paisNativo;
    //Con PAIS [<-reside_en->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="paisResidencia_id",
            foreignKey = @ForeignKey(name="fk_user_paisResidencia")
    )
    private Pais paisResidencia;

    //Con TALENTO [<-->] (1-M).
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Talento> listaTalentos = new ArrayList<>();

    //Con USER [<-marca_como_favorito->] (M-M).
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "marca_como_favorito",
            joinColumns = @JoinColumn(name = "usuarioId_marcador"),
            inverseJoinColumns = @JoinColumn(name = "usuarioFavoritoId"),
            foreignKey = @ForeignKey(name = "fk_usuario_usuarioFavorito"),
            inverseForeignKey = @ForeignKey(name = "fk_usuarioFavorito_usuario")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<User> listaUsuariosFavoritos = new HashSet<>();
    //Con USER [<-marca_como_favorito->] (M-M).
    @ManyToMany(mappedBy = "listaUsuariosFavoritos", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private Set<User> listaUsuariosSeguidores = new HashSet<>();

    //Con INTERCAMBIO [<-->] (1-M).
    @OneToMany(mappedBy = "usuarioDemandante", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Intercambio> intercambiosDemandados = new ArrayList<>();
    //Con INTERCAMBIO [<-->] (1-M).
    @OneToMany(mappedBy = "usuarioSolicitado", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Intercambio> intercambiosRecibidos = new ArrayList<>();

    //Con VALORACION [<-escribe->] (1-M).
    @OneToMany(mappedBy = "usuarioEscritor", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Valoracion> listaValoracionesRealizadas = new ArrayList<>();
    //Con VALORACION [<-recibe->] (1-M).
    @OneToMany(mappedBy = "usuarioValorado", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Valoracion> listaValoracionesRecibidas = new ArrayList<>();

    //Con TAG (MU - MT).
    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "se_etiqueta_con",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            foreignKey = @ForeignKey(name = "fk_usuario_tag"),
            inverseForeignKey = @ForeignKey(name = "fk_tag_usuario")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<TagPRUEBA> listaTags = new HashSet<>();*/

    //MÉTODOS HELPER --------------------------------------------------------------------------------

    //Con USER
    public void addUsuarioFavorito(User u){
        this.listaUsuariosFavoritos.add(u);
        u.getListaUsuariosSeguidores().add(this);
    }
    public void removeUsuarioFavorito(User u){
        u.getListaUsuariosSeguidores().remove(this);
        this.listaUsuariosFavoritos.remove(u);
    }
    //Con USER
    public void addUsuarioSeguidor(User u){
        this.listaUsuariosSeguidores.add(u);
        u.getListaUsuariosFavoritos().add(this);
    }
    public void removeUsuarioSeguidor(User u){
        u.getListaUsuariosFavoritos().remove(this);
        this.listaUsuariosSeguidores.remove(u);
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

    //CON INTERCAMBIO:
    public void addIntercambioDemandado(Intercambio i) {
        this.intercambiosDemandados.add(i);
        i.setUsuarioDemandante(this);
    }
    public void removeIntercambioDemandado(Intercambio i) {
        this.intercambiosDemandados.remove(i);
        i.setUsuarioDemandante(null);
    }
    //CON INTERCAMBIO:
    public void addIntercambioRecibido(Intercambio i) {
        this.intercambiosRecibidos.add(i);
        i.setUsuarioSolicitado(this);
    }
    public void removeIntercambioRecibido(Intercambio i) {
        this.intercambiosRecibidos.remove(i);
        i.setUsuarioSolicitado(null);
    }

    //Con VALORACION:
    public void addValoracionRealizada(Valoracion v){
        v.setUsuarioEscritor(this);
        this.getListaValoracionesRealizadas().add(v);
    }
    public void removeValoracionRealizada(Valoracion v){
        this.getListaValoracionesRealizadas().remove(v);
        v.setUsuarioEscritor(null);
    }
    //Con VALORACION:
    public void addValoracionRecibida(Valoracion v){
        v.setUsuarioValorado(this);
        this.getListaValoracionesRecibidas().add(v);
    }
    public void removeValoracionRecibida(Valoracion v){
        this.getListaValoracionesRecibidas().remove(v);
        v.setUsuarioValorado(null);
    }

    //Con TAG:

    /*public void addTag(TagPRUEBA t){
        this.listaTags.add(t);
        t.getListaUsuarios().add(this);
    }

    public void removeTag(TagPRUEBA t){
        t.getListaUsuarios().remove(this);
        this.listaTags.remove(t);
    }

    */

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
