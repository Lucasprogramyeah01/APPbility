package com.example.APPbility.repository;

import com.example.APPbility.dto.talentoPRUEBA.GetTalentoDTO;
import com.example.APPbility.model.TalentoPRUEBA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TalentoPRUEBARepository extends JpaRepository<TalentoPRUEBA, Long> {

    boolean existsTalentoByUsuario_Id(UUID id);

    @Query("""
       SELECT new com.example.APPbility.dto.talentoPRUEBA.GetTalentoDTO(
            t.id, t.titulo, t.descripcion
        )
        FROM TalentoPRUEBA t JOIN t.usuario u
        WHERE u.id = ?1
    """)
    List<GetTalentoDTO> findListaTalentosByUsuarioID(UUID id);

    /*@Query("""
       SELECT new com.example.APPbility.dto.talentoPRUEBA.GetTalentoDTO(
            t.id, t.titulo, t.descripcion, t.listaImagenes
        )
        FROM Talento t JOIN t.usuario u
        WHERE u.id = ?1
    """)
    List<GetTalentoDTO> findListaTalentosByUsuarioID(UUID id);*/

}
