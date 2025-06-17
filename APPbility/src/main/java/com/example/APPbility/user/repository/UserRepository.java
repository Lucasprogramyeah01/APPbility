package com.example.APPbility.user.repository;

import com.example.APPbility.model.Pais;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /*@Query("""
        SELECT u
        FROM User u
        WHERE u.roles = com.example.APPbility.user.model.UserRole.USER
    """)
    Page<User> findListaUsuariosConRolUser(Pageable pageable);*/

    Page<User> findByRoles(UserRole rol, Pageable pageable);

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

    //SEGURIDAD ---------------------------------------------------------------------------

    Optional<User> findFirstByUsername(String username);

    Optional<User> findByActivationToken(String activationToken);

}
