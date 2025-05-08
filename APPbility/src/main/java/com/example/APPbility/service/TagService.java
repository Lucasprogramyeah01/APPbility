package com.example.APPbility.service;

import com.example.APPbility.dto.tagPRUEBA.EditTagCmd;
import com.example.APPbility.dto.tagPRUEBA.GetTagDTO;
import com.example.APPbility.error.TagPRUEBANotFoundException;
import com.example.APPbility.model.TagPRUEBA;
import com.example.APPbility.repository.TagPRUEBARepository;
import com.example.APPbility.user.dto.GetUserDTO;
import com.example.APPbility.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagPRUEBARepository tagPRUEBARepository;
    private final UserRepository userRepository;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public Set<GetUserDTO> getListaUsuariosByTagID(Long id){
        return userRepository.findListaUsuariosByTagID(id);
    }

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Tags.
    public Page<GetTagDTO> findAll(Pageable pageable){
        Page<GetTagDTO> result = tagPRUEBARepository.findAllTagDTO(pageable);

        if(result.isEmpty())
            throw new TagPRUEBANotFoundException();
        return result;
    }

    //Buscar Tag por ID.
    public TagPRUEBA findById(Long id){
        Optional<TagPRUEBA> tagOptional = tagPRUEBARepository.findById(id);

        if(tagOptional.isPresent())
            return tagOptional.get();
        throw new TagPRUEBANotFoundException(id);
    }

    //Crear Tag.
    public TagPRUEBA save(EditTagCmd nuevo){
        return tagPRUEBARepository.save(TagPRUEBA.builder()
                .nombre(nuevo.nombre())
                .build());
    }

    //Editar Tag.
    public TagPRUEBA edit(EditTagCmd editTagCmd, Long id){
        Optional<TagPRUEBA> tagOptional = tagPRUEBARepository.findById(id);

        if(tagOptional.isPresent()){
            return tagOptional
                .map(old -> {
                    old.setNombre(editTagCmd.nombre());

                    return tagPRUEBARepository.save(old);
                }).get();
        }else{
            throw new TagPRUEBANotFoundException("No se ha encontrado ningún Tag con ID: "+id+".");
        }
    }



}
