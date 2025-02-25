package com.example.APPbility.service;

import com.example.APPbility.dto.talento.EditTalentoCmd;
import com.example.APPbility.error.TagNotFoundException;
import com.example.APPbility.error.TalentoNotFoundException;
import com.example.APPbility.files.model.FileMetadata;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.model.Talento;
import com.example.APPbility.repository.TalentoRepository;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TalentoService {

    private final TalentoRepository talentoRepository;
    private final StorageService storageService;
    private final UserRepository usuarioRepository;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Crear Talento.
    public Talento save(User user, EditTalentoCmd nuevo, MultipartFile... listaMultipartFile){

        for (MultipartFile imagen : listaMultipartFile) {
            FileMetadata fileMetadata = storageService.store(imagen);
            nuevo.listaImagenes().add(fileMetadata.getFilename());
        }

        Talento t = Talento.builder()
                .titulo(nuevo.titulo())
                .descripcion(nuevo.descripcion())
                .listaImagenes(nuevo.listaImagenes())
                .build();

        user.addTalento(t);
        //talentoRepository.save(t);

        //user.addTalento(t);
        //usuarioRepository.save(user);

        return talentoRepository.save(t);
    }

    public Talento save(User user, EditTalentoCmd nuevo){

        Talento t = Talento.builder()
                .titulo(nuevo.titulo())
                .descripcion(nuevo.descripcion())
                .listaImagenes(nuevo.listaImagenes())
                .build();

        user.addTalento(t);
        //talentoRepository.save(t);

        //user.addTalento(t);
        //usuarioRepository.save(user);

        return talentoRepository.save(t);
    }

    //Editar Tag.
    public Talento edit(EditTalentoCmd editTalentoCmd, Long id, MultipartFile... listaMultipartFile) {
        Optional<Talento> talentoOptional = talentoRepository.findById(id);

        for(MultipartFile imagen : listaMultipartFile){
            FileMetadata fileMetadata = storageService.store(imagen);
            editTalentoCmd.listaImagenes().add(fileMetadata.getFilename());
        }

        if (talentoOptional.isPresent()) {
            return talentoOptional
                    .map(old -> {
                        old.setTitulo(editTalentoCmd.titulo());
                        old.setDescripcion(editTalentoCmd.descripcion());
                        old.getListaImagenes().addAll(editTalentoCmd.listaImagenes());

                        return talentoRepository.save(old);
                    }).get();
        } else {
            throw new TalentoNotFoundException("No se ha encontrado ningún Talento con ID: " + id + ".");
        }
    }

}
