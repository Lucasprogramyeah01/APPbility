package com.example.APPbility.model;

import com.example.APPbility.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@IdClass(IntercambioPK.class)
public class Intercambio {

    @Id
    @GeneratedValue
    private Long intercambioID;

    private LocalDateTime fechaSolicitud;

    private LocalDateTime fechaComienzo;

    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private boolean finalizadoPorDemandante = false;

    private boolean finalizadoPorSolicitado = false;

    //ASOCIACIONES ----------------------------------------------------------------------------------

    //Con USER [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="usuarioDemandante_id",
            foreignKey = @ForeignKey(name="fk_intercambio_usuarioDemandante")
    )
    private User usuarioDemandante;
    //Con USER [<-->] (M-1).
    @ManyToOne
    @JoinColumn(
            name="usuarioSolicitado_id",
            foreignKey = @ForeignKey(name="fk_intercambio_usuarioSolicitado")
    )
    private User usuarioSolicitado;

    //Con TALENTO [T-->I] (M-1).
    @ManyToOne
    @JoinColumn(
            name="talentoSolicitado_id",
            foreignKey = @ForeignKey(name="fk_intercambio_talentoSolicitado")
    )
    private Talento talentoSolicitado;
    //Con TALENTO [T-->I] (M-1).
    @ManyToOne
    @JoinColumn(
            name="talentoSugerido_id",
            foreignKey = @ForeignKey(name="fk_intercambio_talentoSugerido")
    )
    private Talento talentoSugerido;
    //Con TALENTO [T-->I] (M-1).
    @ManyToOne
    @JoinColumn(
            name="talentoAceptado_id",
            foreignKey = @ForeignKey(name="fk_intercambio_talentoAceptado")
    )
    private Talento talentoAceptado;

    //Con SESION [<-->] (1-M).
    @OneToMany(mappedBy = "intercambio", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Sesion> listaSesiones = new ArrayList<>();

    //Con MENSAJECHAT [<-->] (1-M).
    @OneToMany(mappedBy = "intercambio", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<MensajeChat> listaMensajesChat = new ArrayList<>();

    //Con VALORACION [<-->] (1-M).
    @OneToMany(mappedBy = "intercambio", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Valoracion> listaValoraciones = new ArrayList<>();

    //MÉTODOS HELPER --------------------------------------------------------------------------------

    //Con SESION:
    public void addSesion(Sesion s){
        s.setIntercambio(this);
        this.getListaSesiones().add(s);
    }
    public void removeSesion(Sesion s){
        this.getListaSesiones().remove(s);
        s.setIntercambio(null);
    }

    //Con MENSAJECHAT:
    public void addMensajeChat(MensajeChat m){
        m.setIntercambio(this);
        this.getListaMensajesChat().add(m);
    }
    public void removeMensajeChat(MensajeChat m){
        this.getListaMensajesChat().remove(m);
        m.setIntercambio(null);
    }

    //Con VALORACION:
    public void addValoracion(Valoracion v){
        v.setIntercambio(this);
        this.getListaValoraciones().add(v);
    }
    public void removeValoracion(Valoracion v){
        this.getListaValoraciones().remove(v);
        v.setIntercambio(null);
    }

    //EQUALS & HASHCODE ----------------------------------------------------------------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Intercambio that = (Intercambio) o;
        return getIntercambioID() != null && Objects.equals(getIntercambioID(), that.getIntercambioID());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
