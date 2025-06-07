package com.example.APPbility.repository;

import com.example.APPbility.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SesionRepository extends JpaRepository<Sesion, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
        FROM Sesion s
        WHERE s.intercambio.intercambioID = ?1 AND s.fecha = ?2
    """)
    boolean existsByIntercambioIdAndFecha(Long intercambioId, LocalDate fecha);

}
