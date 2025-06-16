package com.example.APPbility.repository;

import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContinenteRepository extends JpaRepository<Continente, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    @Query("""
        SELECT p
        FROM Pais p
        WHERE p.continente.id = ?1
        ORDER BY p.nombre ASC
        """)
    List<Pais> findListaPaisesByContinenteID(Long id);

}
