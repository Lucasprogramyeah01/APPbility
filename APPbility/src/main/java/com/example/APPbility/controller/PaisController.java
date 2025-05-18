package com.example.APPbility.controller;

import com.example.APPbility.dto.continente.CreateContinenteCMD;
import com.example.APPbility.dto.continente.EditContinenteCMD;
import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.dto.pais.*;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import com.example.APPbility.service.ContinenteService;
import com.example.APPbility.service.PaisService;
import com.example.APPbility.user.dto.GetUserDTO;
import com.example.APPbility.user.dto.GetUserSinPaisNativoDTO;
import com.example.APPbility.user.dto.GetUserSinPaisResidenciaDTO;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/pais/")
@Tag(name = "Pais", description = "Controlador de Pais, para poder realizar sus operaciones de gestión.")
public class PaisController {

    private final PaisService paisService;
    private final ContinenteService continenteService;
    private final UserService userService;

    @GetMapping
    public Page<GetPaisDTO> findAll(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable){
        return paisService.findAll(pageable).map(GetPaisDTO::of);
    }

    @GetMapping("{id}")
    public GetPaisDTOCompleto findByID(@PathVariable Long id){
        Pais p = paisService.findById(id);

        GetContinenteDTO getContinenteDTO = GetContinenteDTO.of(paisService.getContinenteByPaisID(id));
        List<GetUserSinPaisNativoDTO> listaUsuariosNativos = paisService.getListaUsuariosNativosByPaisID(id).stream()
            .map(user -> GetUserSinPaisNativoDTO.of(user, GetPaisDTO.of(user.getPaisResidencia()))).toList();
        List<GetUserSinPaisResidenciaDTO> listaUsuariosResidentes = paisService.getListaUsuariosResidentesByPaisID(id).stream()
            .map(user -> GetUserSinPaisResidenciaDTO.of(user, GetPaisDTO.of(user.getPaisNativo()))).toList();

        return GetPaisDTOCompleto.of(p, getContinenteDTO, listaUsuariosNativos, listaUsuariosResidentes);
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GetPaisDTOConContinente> save(@Valid @RequestPart("pais") CreatePaisCMD nuevo,
        @RequestPart(value = "bandera", required = true) MultipartFile multipartFile){
        Pais nuevoPais = paisService.save(nuevo, multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(GetPaisDTOConContinente.of(nuevoPais, GetContinenteDTO.of(paisService.getContinenteByPaisID(nuevoPais.getId()))));
    }

    @PutMapping(value = "{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public GetPaisDTOConContinente edit(@Valid @RequestPart("pais") EditPaisCMD editPaisCMD,
        @RequestPart(value = "bandera", required = true) MultipartFile multipartFile, @PathVariable Long id){
        Pais paisEditado = paisService.edit(editPaisCMD, multipartFile, id);

        return GetPaisDTOConContinente.of(paisEditado, GetContinenteDTO.of(paisService.getContinenteByPaisID(id)));
    }


}
