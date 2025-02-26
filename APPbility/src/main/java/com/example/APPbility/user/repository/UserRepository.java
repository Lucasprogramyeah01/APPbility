package com.example.APPbility.user.repository;

import com.example.APPbility.model.Talento;
import com.example.APPbility.user.dto.GetUserDTO;
import com.example.APPbility.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
        SELECT u.listaTalentos
        FROM User u
        WHERE u.id = ?1
    """)
    List<Talento> findListaTalentosByUsuarioID(UUID id);

    @Query("""
        SELECT new com.example.APPbility.user.dto.GetUserDTO(
            u.id, u.username, u.email, u.nombre, u.apellidos, u.sexo, u.numTelefono,
            u.imagenPerfil, u.fechaNacimiento, u.lugarNacimiento, u.lugarResidencia,
            u.puntosPopularidad, u.idiomaNativo, u.otrosIdiomas, u.conocimientos,
            u.descripcion
        )
        FROM User u
    """)
    Page<GetUserDTO> findAllUserDTO(Pageable pageable);

    @Query("""
         SELECT NEW com.example.APPbility.user.dto.GetUserDTO(
            u.id, u.username, u.email, u.nombre, u.apellidos, u.sexo, u.numTelefono,
            u.imagenPerfil, u.fechaNacimiento, u.lugarNacimiento, u.lugarResidencia,
            u.puntosPopularidad, u.idiomaNativo, u.otrosIdiomas, u.conocimientos,
            u.descripcion
         )
         FROM User u JOIN u.listaUsuariosFavoritos luf
         WHERE luf.id = ?1
    """)
    Set<GetUserDTO> findListaUsuariosFavoritosByUsuarioID(UUID id);

    @Query("""
         SELECT NEW com.example.APPbility.user.dto.GetUserDTO(
            u.id, u.username, u.email, u.nombre, u.apellidos, u.sexo, u.numTelefono,
            u.imagenPerfil, u.fechaNacimiento, u.lugarNacimiento, u.lugarResidencia,
            u.puntosPopularidad, u.idiomaNativo, u.otrosIdiomas, u.conocimientos,
            u.descripcion
         )
         FROM User u JOIN u.listaUsuariosSeguidores lus
         WHERE lus.id = ?1
    """)
    Set<GetUserDTO> findListaUsuariosSeguidoresByUsuarioID(UUID id);

    @Query("""
         SELECT NEW com.example.APPbility.user.dto.GetUserDTO(
            u.id, u.username, u.email, u.nombre, u.apellidos, u.sexo, u.numTelefono,
            u.imagenPerfil, u.fechaNacimiento, u.lugarNacimiento, u.lugarResidencia,
            u.puntosPopularidad, u.idiomaNativo, u.otrosIdiomas, u.conocimientos,
            u.descripcion
         )
         FROM User u JOIN u.listaTags lt
         WHERE lt.id = ?1
    """)
    Set<GetUserDTO> findListaUsuariosByTagID(Long id);

    //SEGURIDAD ---------------------------------------------------------------------------

    Optional<User> findFirstByUsername(String username);

    Optional<User> findByActivationToken(String activationToken);

}
