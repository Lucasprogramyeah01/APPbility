package com.example.APPbility.user.model;

import com.example.APPbility.model.enums.Provincia;
import com.example.APPbility.model.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    private String apellidos;

    private Sexo sexo;

    private byte[] imagenPerfil;

    private LocalDate fechaNacimiento;

    private Provincia lugarNacimiento;

    private Provincia lugarResidencia;

    private Long puntosPopularidad;

    private String idiomas;

    private String conocimientos;

    @Lob
    private String descripcion;

    @Column(unique = true, updatable = false)
    private String username;

    private String password;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @Builder.Default
    private boolean enabled = false;

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



}
