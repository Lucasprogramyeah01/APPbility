package com.example.APPbility.repository;

import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaisRepository extends JpaRepository<Pais, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByCodigoISOIgnoreCase(String codigoISO);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
    boolean existsByCodigoISOIgnoreCaseAndIdNot(String codigoISO, Long id);

    @Query("""
        SELECT c
        FROM Pais p JOIN p.continente c
        WHERE p.id = ?1
        """)
    Continente findContinenteByPaisID(Long id);

}
