package com.example.APPbility.repository;

import com.example.APPbility.model.Nivel;
import com.example.APPbility.model.Talento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TalentoRepository extends JpaRepository<Talento, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM Talento t
        WHERE t.usuario.id = ?2
            AND LOWER(t.titulo) = LOWER(?1)
    """)
    boolean existsByUsuarioIdAndTituloIgnoreCase(String titulo, UUID id);

    @Query("""
        SELECT t
        FROM Talento t
        WHERE t.usuario.id = ?1
        ORDER BY t.nivel.orden DESC
    """)
    Page<Talento> findListaTalentosByUsuarioIDOrderedByNivelOrdenDesc(UUID id, Pageable pageable);

    @Query("""
        SELECT n
        FROM Talento t JOIN t.nivel n
        WHERE t.id = ?1
        """)
    Nivel findNivelByTalentoID(Long id);

}
