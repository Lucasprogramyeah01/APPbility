package com.example.APPbility.service;

import com.example.APPbility.dto.continente.CreateContinenteCMD;
import com.example.APPbility.dto.pais.CreatePaisCMD;
import com.example.APPbility.error.ContinenteNotFoundException;
import com.example.APPbility.error.PaisNotFoundException;
import com.example.APPbility.error.TagPRUEBANotFoundException;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import com.example.APPbility.repository.ContinenteRepository;
import com.example.APPbility.repository.PaisRepository;
import com.example.APPbility.user.error.UserNotFoundException;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository paisRepository;
    private final UserRepository userRepository;
    private final ContinenteRepository continenteRepository;

    private final StorageService storageService;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public Continente getContinenteByPaisID(Long id){
        return paisRepository.findContinenteByPaisID(id);
    }

    public List<User> getListaUsuariosNativosByPaisID(Long id){
        return userRepository.findListaUsuariosNativosByPaisID(id);
    }

    public List<User> getListaUsuariosResidentesByPaisID(Long id){
        return userRepository.findListaUsuariosResidentesByPaisID(id);
    }

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Paises.
    public Page<Pais> findAll(Pageable pageable){
        Page<Pais> result = paisRepository.findAll(pageable);

        if(result.isEmpty())
            throw new PaisNotFoundException();
        return result;
    }

    //Buscar Pais por ID.
    public Pais findById(Long id){
        Optional<Pais> paisOptional = paisRepository.findById(id);

        if(paisOptional.isPresent())
            return paisOptional.get();
        throw new PaisNotFoundException(id);
    }

    //Crear Pais.
    public Pais save(CreatePaisCMD nuevo, MultipartFile multipartFile){
        Continente continente = continenteRepository.findById(nuevo.continenteID())
            .orElseThrow(() -> new ContinenteNotFoundException("No se ha encontrado ningún continente con ese ID: "
                +nuevo.continenteID()+".")
            );

        String bandera = "/uploads/" + storageService.storeInFolder(multipartFile, "banderasPaises")
            .getFilename();

        return  paisRepository.save(Pais.builder()
                .nombre(nuevo.nombre())
                .codigoISO(nuevo.codigoISO())
                .bandera(bandera)
                .continente(continente)
                .build());
    }


}
