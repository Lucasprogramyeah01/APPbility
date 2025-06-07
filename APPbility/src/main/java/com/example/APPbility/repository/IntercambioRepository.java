package com.example.APPbility.repository;

import com.example.APPbility.model.Estado;
import com.example.APPbility.model.Intercambio;
import com.example.APPbility.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IntercambioRepository extends JpaRepository<Intercambio, Long> {

    @Query("""
        SELECT COUNT(i) > 0 FROM Intercambio i
        WHERE (i.usuarioDemandante = ?1 AND i.usuarioSolicitado = ?2
               OR i.usuarioDemandante = ?2 AND i.usuarioSolicitado = ?1)
         AND i.estado IN (?3)
    """)
    boolean existsIntercambioEntreUsuariosConEstados(User usuario1, User usuario2, List<Estado> estados);

    @Query("""
        SELECT i
        FROM Intercambio i
        WHERE i.usuarioDemandante.id = ?1
            OR i.usuarioSolicitado.id = ?1
    """)
    Page<Intercambio> findByUsuarioDemandanteIdOrUsuarioSolicitadoId(UUID usuarioID, Pageable pageable);
}
