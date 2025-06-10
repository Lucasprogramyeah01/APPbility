package com.example.APPbility.user.repository;

import com.example.APPbility.model.Pais;
import com.example.APPbility.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
        SELECT u
        FROM User u JOIN u.paisNativo p
        WHERE p.id = ?1
    """)
    List<User> findListaUsuariosNativosByPaisID(Long id);

    @Query("""
        SELECT u
        FROM User u JOIN u.paisResidencia p
        WHERE p.id = ?1
    """)
    List<User> findListaUsuariosResidentesByPaisID(Long id);

    @Query("""
        SELECT p
        FROM User u JOIN u.paisNativo p
        WHERE u.id = ?1
    """)
    Pais findPaisNativoByUsuarioID(UUID id);

    @Query("""
        SELECT p
        FROM User u JOIN u.paisResidencia p
        WHERE u.id = ?1
    """)
    Pais findPaisResidenciaByUsuarioID(UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    /*@Query("""
        SELECT u.listaTalentos
        FROM User u
        WHERE u.id = ?1
    """)
    List<TalentoPRUEBA> findListaTalentosByUsuarioID(UUID id);*/

    /*@Query("""
        SELECT NEW com.example.APPbility.user.dto.GetUserDTO(
            u.id, u.username, u.password, u.email, u.nombre, u.apellidos, u.fechaNacimiento, u.sexo, 
            u.modalidadPreferida, u.numTelefono, u.mostrarNumTelefono, u.imagenPerfil, u.idiomaNativo, 
            u.listaOtrosIdiomas, u.descripcionProfesional, u.presentacionPersonal, u.listaEnlacesExternos, 
            u.paisNativo, u.paisResidencia  
        )
        FROM User u
    """)
    Page<User> findAllUserDTO(Pageable pageable);*/

    /*@Query("""
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
    Set<GetUserDTO> findListaUsuariosByTagID(Long id);*/

    //SEGURIDAD ---------------------------------------------------------------------------

    Optional<User> findFirstByUsername(String username);

    Optional<User> findByActivationToken(String activationToken);

}
