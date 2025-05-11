package com.example.APPbility.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagPRUEBAService {

    /*private final TagPRUEBARepository tagPRUEBARepository;
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
    }*/



}
