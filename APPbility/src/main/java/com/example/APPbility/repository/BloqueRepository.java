package com.example.APPbility.repository;

import com.example.APPbility.model.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloqueRepository extends JpaRepository<Bloque, Long> {

    @Query("""
        SELECT b
        FROM Bloque b
        WHERE b.sesion.id = ?1
    """)
    List<Bloque> findBloquesBySesionId(Long sesionId);

}
