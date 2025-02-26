package com.example.APPbility.repository;

import com.example.APPbility.dto.valoracion.GetValoracionDTO;
import com.example.APPbility.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    @Query("""
       SELECT new com.example.APPbility.dto.valoracion.GetValoracionDTO(
            v.id, v.puntuacion, v.titulo, v.resenha
        )
        FROM Valoracion v JOIN v.usuarioEscritor ue
        WHERE ue.id = ?1
    """)
    List<GetValoracionDTO> findListaValoracionesRealizadasByUsuarioID(UUID id);

    @Query("""
       SELECT new com.example.APPbility.dto.valoracion.GetValoracionDTO(
            v.id, v.puntuacion, v.titulo, v.resenha
        )
        FROM Valoracion v JOIN v.usuarioValorado uv
        WHERE uv.id = ?1
    """)
    List<GetValoracionDTO> findListaValoracionesRecibidasByUsuarioID(UUID id);

}
