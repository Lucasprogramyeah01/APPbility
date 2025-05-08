package com.example.APPbility.service;

import com.example.APPbility.dto.talentoPRUEBA.EditTalentoCmd;
import com.example.APPbility.error.TalentoPRUEBANotFoundException;
import com.example.APPbility.files.model.FileMetadata;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.model.TalentoPRUEBA;
import com.example.APPbility.repository.TalentoPRUEBARepository;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TalentoService {

    private final TalentoPRUEBARepository talentoPRUEBARepository;
    private final StorageService storageService;
    private final UserRepository userRepository;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    public boolean existsTalentoByUsuario_Id(UUID id){
        return talentoPRUEBARepository.existsTalentoByUsuario_Id(id);
    }

    //Crear Talento.
    public TalentoPRUEBA save(User user, EditTalentoCmd nuevo, MultipartFile... listaMultipartFile){

        List<TalentoPRUEBA> ut = userRepository.findListaTalentosByUsuarioID(user.getId());

        for (MultipartFile imagen : listaMultipartFile) {
            FileMetadata fileMetadata = storageService.store(imagen);
            nuevo.listaImagenes().add(fileMetadata.getFilename());
        }

        TalentoPRUEBA t = TalentoPRUEBA.builder()
                .titulo(nuevo.titulo())
                .descripcion(nuevo.descripcion())
                .listaImagenes(nuevo.listaImagenes())
                .build();

        ut.add(t);
        t.setUsuario(user);

        //talentoRepository.save(t);

        //user.addTalento(t);
        //usuarioRepository.save(user);

        talentoPRUEBARepository.save(t);

        return talentoPRUEBARepository.save(t);
    }

    public TalentoPRUEBA save(User user, EditTalentoCmd nuevo){

        TalentoPRUEBA t = TalentoPRUEBA.builder()
                .titulo(nuevo.titulo())
                .descripcion(nuevo.descripcion())
                .listaImagenes(nuevo.listaImagenes())
                .build();

        user.addTalento(t);
        //talentoRepository.save(t);

        //user.addTalento(t);
        //usuarioRepository.save(user);

        return talentoPRUEBARepository.save(t);
    }

    //Editar Tag.
    public TalentoPRUEBA edit(EditTalentoCmd editTalentoCmd, Long id, MultipartFile... listaMultipartFile) {
        Optional<TalentoPRUEBA> talentoOptional = talentoPRUEBARepository.findById(id);

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

                        return talentoPRUEBARepository.save(old);
                    }).get();
        } else {
            throw new TalentoPRUEBANotFoundException("No se ha encontrado ningún Talento con ID: " + id + ".");
        }
    }

}
