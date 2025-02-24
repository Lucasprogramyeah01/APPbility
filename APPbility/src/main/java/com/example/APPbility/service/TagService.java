package com.example.APPbility.service;

import com.example.APPbility.dto.tag.EditTagCmd;
import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.error.TagNotFoundException;
import com.example.APPbility.model.Tag;
import com.example.APPbility.repository.TagRepository;
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

    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public Set<GetUserDTO> getListaUsuariosByTagID(Long id){
        return userRepository.findListaUsuariosByTagID(id);
    }

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Tags.
    public Page<GetTagDTO> findAll(Pageable pageable){
        Page<GetTagDTO> result = tagRepository.findAllTagDTO(pageable);

        if(result.isEmpty())
            throw new TagNotFoundException();
        return result;
    }

    //Buscar Tag por ID.
    public Tag findById(Long id){
        Optional<Tag> tagOptional = tagRepository.findById(id);

        if(tagOptional.isPresent())
            return tagOptional.get();
        throw new TagNotFoundException(id);
    }

    //Crear Tag.
    public Tag save(EditTagCmd nuevo){
        return tagRepository.save(Tag.builder()
                .nombre(nuevo.nombre())
                .build());
    }

    public Tag edit(EditTagCmd editTagCmd, Long id){
        Optional<Tag> tagOptional = tagRepository.findById(id);

        if(tagOptional.isPresent()){
            return tagOptional
                .map(old -> {
                    old.setNombre(editTagCmd.nombre());

                    return tagRepository.save(old);
                }).get();
        }else{
            throw new TagNotFoundException("No se ha encontrado ningún Tag con ID: "+id+".");
        }
    }



}
