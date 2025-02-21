package com.example.APPbility.repository;

import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("""
        SELECT new com.example.APPbility.dto.tag.GetTagDTO(
            t.id, t.nombre
        )
        FROM Tag t
    """)
    List<GetTagDTO> findAllTagDTO();

}
