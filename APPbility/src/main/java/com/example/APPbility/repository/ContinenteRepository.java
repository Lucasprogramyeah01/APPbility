package com.example.APPbility.repository;

import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContinenteRepository extends JpaRepository<Continente, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    @Query("""
        SELECT c.listaPaises 
        FROM Continente c
        WHERE c.id = ?1
        """)
    List<Pais> findListaPaisesByContinenteID(Long id);

}
