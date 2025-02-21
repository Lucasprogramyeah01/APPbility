package com.example.APPbility.service;

import com.example.APPbility.dto.tag.GetTagDTO;
import com.example.APPbility.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    //Listar todos los Tags.
    public List<GetTagDTO> findAll(){
        List<GetTagDTO> result = tagRepository.findAllTagDTO();

        if(result.isEmpty())
            throw new EntityNotFoundException();
        return result;
    }

}
