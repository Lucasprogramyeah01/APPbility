package com.example.APPbility.repository;

import com.example.APPbility.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NivelRepository extends JpaRepository<Nivel, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByColorIgnoreCase(String color);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
    boolean existsByColorIgnoreCaseAndIdNot(String color, Long id);

    @Query("SELECT n FROM Nivel n ORDER BY n.orden ASC")
    List<Nivel> findListaNivelesOrderedByOrden();

}
