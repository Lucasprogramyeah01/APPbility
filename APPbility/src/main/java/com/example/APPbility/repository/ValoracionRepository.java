package com.example.APPbility.repository;

import com.example.APPbility.model.Valoracion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    @Query("""
        SELECT COUNT(v) > 0
        FROM Valoracion v
        WHERE v.intercambio.intercambioID = ?1
        AND v.usuarioEscritor.id = ?2
    """)
    boolean existsByIntercambioIDAndUsuarioEscritorID(Long intercambioID, UUID usuarioEscritorID);

    @Query("""
    SELECT v FROM Valoracion v
    WHERE v.usuarioEscritor.id = ?1
       OR v.usuarioValorado.id = ?1
    """)
    Page<Valoracion> findAllValoracionesByUsuarioID(UUID id, Pageable pageable);

}
