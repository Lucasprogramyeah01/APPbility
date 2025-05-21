package com.example.APPbility.service;

import com.example.APPbility.dto.pais.CreatePaisCMD;
import com.example.APPbility.dto.pais.EditPaisCMD;
import com.example.APPbility.error.custom.DuplicatedAttributeException;
import com.example.APPbility.error.custom.EntityWithRelationshipsException;
import com.example.APPbility.error.custom.IncorrectSizeException;
import com.example.APPbility.error.entity.ContinenteNotFoundException;
import com.example.APPbility.error.entity.PaisNotFoundException;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import com.example.APPbility.repository.ContinenteRepository;
import com.example.APPbility.repository.PaisRepository;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
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
        return paisRepository.findById(id).orElseThrow(() -> new PaisNotFoundException(id));
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
                .codigoISO(nuevo.codigoISO().toUpperCase())
                .bandera(bandera)
                .continente(continente)
                .build());
    }

    //Editar Pais.
    public Pais edit(EditPaisCMD editPaisCMD, MultipartFile multipartFile, Long id){
        Pais pais = paisRepository.findById(id).orElseThrow(() -> new PaisNotFoundException(id));

        //Alternativa de la validación "UniqueNombrePaisEdit".
        boolean nombreDuplicado = paisRepository.existsByNombreIgnoreCaseAndIdNot(editPaisCMD.nombre().trim(), id);
        if (nombreDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un pais registrado con ese nombre.");
        }

        //Alternativa de la validación "@Size(min = 2, max = 2, message = "{pais.codigoISO.size}")".
        String codigoISO = editPaisCMD.codigoISO().trim();
        if (codigoISO.length() != 2) {
            throw new IncorrectSizeException("El código ISO de un país debe contener exactamente 2 caracteres.");
        }

        //Alternativa de la validación "UniqueCodigoISOPaisEdit".
        boolean codigoISODuplicado = paisRepository.existsByCodigoISOIgnoreCaseAndIdNot(editPaisCMD.codigoISO().trim(), id);
        if (codigoISODuplicado) {
            throw new DuplicatedAttributeException("Ya existe un país registrado con ese código ISO.");
        }

        Continente continente = continenteRepository.findById(editPaisCMD.continenteID())
            .orElseThrow(() -> new ContinenteNotFoundException("No se ha encontrado ningún continente con ese ID: "
                    +editPaisCMD.continenteID()+".")
            );

        String bandera = "/uploads/" + storageService.storeInFolder(multipartFile, "banderasPaises")
                .getFilename();

        if(pais.getBandera().contains("uploads")) {
            String antiguaBandera = Paths.get(pais.getBandera()).getFileName().toString();
            storageService.deleteFileInFolder("banderasPaises", antiguaBandera);
        }

        pais.setNombre(editPaisCMD.nombre().trim());
        pais.setCodigoISO(editPaisCMD.codigoISO().trim().toUpperCase());
        pais.setBandera(bandera);
        pais.setContinente(continente);

        return paisRepository.save(pais);
    }

    //Borrar País.
    public void delete(Long id){
        Pais pais = paisRepository.findById(id).orElseThrow(() -> new PaisNotFoundException(id));

        if(!pais.getListaUsuariosNativos().isEmpty() && !pais.getListaUsuariosResidentes().isEmpty()){
            throw new EntityWithRelationshipsException("No se ha podido eliminar el país con ID: " +
                pais.getId() + " porque tiene usuarios asociados.");
        }

        if(pais.getBandera().contains("uploads")){
            String bandera = Paths.get(pais.getBandera()).getFileName().toString();
            storageService.deleteFileInFolder("banderasPaises", bandera);
        }

        paisRepository.delete(pais);
    }

}
