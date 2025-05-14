package com.example.APPbility.repository;

import com.example.APPbility.dto.tagPRUEBA.GetTagDTO;
import com.example.APPbility.model.TagPRUEBA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface TagPRUEBARepository extends JpaRepository<TagPRUEBA, Long> {

    boolean existsByNombre(String nombre);

    /*@Query("""
        SELECT new com.example.APPbility.dto.tagPRUEBA.GetTagDTO(
            t.id, t.nombre
        )
        FROM TagPRUEBA t
    """)
    Page<GetTagDTO> findAllTagDTO(Pageable pageable);

    @Query("""
       SELECT new com.example.APPbility.dto.tagPRUEBA.GetTagDTO(
            t.id, t.nombre
        )
        FROM TagPRUEBA t JOIN t.listaUsuarios lu
        WHERE lu.id = ?1
    """)
    Set<GetTagDTO> findListaTagsByUsuarioID(UUID id);*/

}
