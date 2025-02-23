package com.example.APPbility.service;

import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.error.TagNotFoundException;
import com.example.APPbility.model.Tag;
import com.example.APPbility.repository.TagRepository;
import com.example.APPbility.user.dto.GetUserDTO;
import com.example.APPbility.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<GetTagDTO> findAll(){
        List<GetTagDTO> result = tagRepository.findAllTagDTO();

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



}
