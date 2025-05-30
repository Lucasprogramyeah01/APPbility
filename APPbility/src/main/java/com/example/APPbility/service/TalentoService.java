package com.example.APPbility.service;

import com.example.APPbility.dto.pais.CreatePaisCMD;
import com.example.APPbility.dto.talento.CreateTalentoCMD;
import com.example.APPbility.error.custom.DuplicatedAttributeException;
import com.example.APPbility.error.custom.EntityWithRelationshipsException;
import com.example.APPbility.error.custom.UnauthorizedAccessException;
import com.example.APPbility.error.entity.ContinenteNotFoundException;
import com.example.APPbility.error.entity.NivelNotFoundException;
import com.example.APPbility.error.entity.PaisNotFoundException;
import com.example.APPbility.error.entity.TalentoNotFoundException;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Nivel;
import com.example.APPbility.model.Pais;
import com.example.APPbility.model.Talento;
import com.example.APPbility.repository.NivelRepository;
import com.example.APPbility.repository.TalentoRepository;
import com.example.APPbility.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TalentoService {

    private final TalentoRepository talentoRepository;
    private final NivelRepository nivelRepository;

    private final StorageService storageService;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public Nivel getNivelByTalentoID(Long id){
        return talentoRepository.findNivelByTalentoID(id);
    }

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Talentos de un Usuario.
    public Page<Talento> findTalentosfromUsuario(UUID id, Pageable pageable){
        Page<Talento> result = talentoRepository.findListaTalentosByUsuarioIDOrderedByNivelOrdenDesc(id, pageable);

        if(result.isEmpty())
            throw new TalentoNotFoundException();
        return result;
    }

    //Crear Talento.
    public Talento save(CreateTalentoCMD nuevo, MultipartFile multipartFile, User user){
        //Alternativa de la validación "UniqueTituloTalento".
        boolean nombreDuplicado = talentoRepository.existsByUsuarioIdAndTituloIgnoreCase(nuevo.titulo().trim(), user.getId());
        if(nombreDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un talento con ese título en tu perfil.");
        }

        Nivel nivel = nivelRepository.findById(nuevo.nivelID())
            .orElseThrow(() -> new NivelNotFoundException(nuevo.nivelID())
        );

        Talento.TalentoBuilder builder = Talento.builder()
                .titulo(nuevo.titulo().trim())
                .descripcion(nuevo.descripcion().trim())
                .nivel(nivel)
                .usuario(user);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String imagen = "/uploads/" + storageService.storeInFolder(multipartFile, "talentos").getFilename();
            builder.imagen(imagen);
        }

        return talentoRepository.save(builder.build());
    }

    //Editar Talento.
    /*En este métod0 se utiliza el CreateTalentoCMD como si fuera un "EditTalentoCMD" porque ambos serían el mismo
    DTO, por lo que CreateTalentoCMD vale tanto para una cosa como para otra.*/
    public Talento edit(CreateTalentoCMD editTalentoCMD, MultipartFile multipartFile, User user, Long id){
        Talento talento = talentoRepository.findById(id).orElseThrow(() -> new TalentoNotFoundException(id));

        //Validación para comprobar si es el usuario es propietario del talento.
        if (!talento.getUsuario().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("No tienes permiso para editar este talento.");
        }

        //Alternativa de la validación "UniqueTituloTalentoEdit".
        boolean nombreDuplicado = talentoRepository.existsByUsuarioIdAndTituloIgnoreCase(editTalentoCMD.titulo().trim(), user.getId());
        if(!talento.getTitulo().equalsIgnoreCase(editTalentoCMD.titulo().trim()) && nombreDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un talento con ese título en tu perfil.");
        }

        Nivel nivel = nivelRepository.findById(editTalentoCMD.nivelID())
                .orElseThrow(() -> new NivelNotFoundException(editTalentoCMD.nivelID())
        );

        if(multipartFile != null && !multipartFile.isEmpty()) {
            String imagen = "/uploads/" + storageService.storeInFolder(multipartFile, "talentos").getFilename();

            if(talento.getImagen() != null && talento.getImagen().contains("uploads")) {
                String antiguaImagen = Paths.get(talento.getImagen()).getFileName().toString();
                storageService.deleteFileInFolder("talentos", antiguaImagen);
                talento.setImagen(imagen);
            }else{
                talento.setImagen(imagen);
            }
        }

        talento.setTitulo(editTalentoCMD.titulo().trim());
        talento.setDescripcion(editTalentoCMD.descripcion().trim());
        talento.setNivel(nivel);

        return talentoRepository.save(talento);
    }

    //Borrar Talento.
    public void delete(Long id, User user){
        Talento talento = talentoRepository.findById(id).orElseThrow(() -> new TalentoNotFoundException(id));

        //Validación para comprobar si es el usuario es propietario del talento.
        if (!talento.getUsuario().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("No tienes permiso para borrar este talento.");
        }

        if(talento.getImagen() != null && talento.getImagen().contains("uploads")){
            String imagen = Paths.get(talento.getImagen()).getFileName().toString();
            storageService.deleteFileInFolder("talentos", imagen);
        }

        talentoRepository.delete(talento);
    }

}
