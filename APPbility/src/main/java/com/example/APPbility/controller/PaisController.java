package com.example.APPbility.controller;

import com.example.APPbility.dto.continente.GetContinenteDTO;
import com.example.APPbility.dto.pais.GetPaisDTO;
import com.example.APPbility.dto.pais.GetPaisDTOCompleto;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Pais;
import com.example.APPbility.service.ContinenteService;
import com.example.APPbility.service.PaisService;
import com.example.APPbility.user.dto.GetUserDTO;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Pais", description = "Controlador de Pais, para poder realizar sus operaciones de gestión.")
public class PaisController {

    private final PaisService paisService;
    private final ContinenteService continenteService;
    private final UserService userService;

    @GetMapping("/pais/")
    public Page<GetPaisDTO> findAll(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable){
        return paisService.findAll(pageable).map(GetPaisDTO::of);
    }

    @GetMapping("/pais/{id}")
    public GetPaisDTOCompleto findByID(@PathVariable Long id){
        GetContinenteDTO getContinenteDTO = GetContinenteDTO.of(paisService.getContinenteByPaisID(id));
        List<GetUserDTO> listaUsuariosNativos = paisService.getListaUsuariosNativosByPaisID(id).stream().map(GetUserDTO::of).toList();
        List<GetUserDTO> listaUsuariosResidentes = paisService.getListaUsuariosResidentesByPaisID(id).stream().map(GetUserDTO::of).toList();

        Pais p = paisService.findById(id);

        return GetPaisDTOCompleto.of(p, getContinenteDTO, listaUsuariosNativos, listaUsuariosResidentes);
    }


}
