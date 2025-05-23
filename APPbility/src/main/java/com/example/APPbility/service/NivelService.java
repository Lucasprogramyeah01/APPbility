package com.example.APPbility.service;

import com.example.APPbility.dto.continente.CreateContinenteCMD;
import com.example.APPbility.dto.nivel.CreateNivelCMD;
import com.example.APPbility.dto.nivel.EditNivelCMD;
import com.example.APPbility.error.custom.DuplicatedAttributeException;
import com.example.APPbility.error.custom.EntityWithRelationshipsException;
import com.example.APPbility.error.custom.IncorrectPatternException;
import com.example.APPbility.error.custom.IncorrectSizeException;
import com.example.APPbility.error.entity.ContinenteNotFoundException;
import com.example.APPbility.error.entity.NivelNotFoundException;
import com.example.APPbility.model.Continente;
import com.example.APPbility.model.Nivel;
import com.example.APPbility.repository.NivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelService {

    private final NivelRepository nivelRepository;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------


    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Niveles.
    public Page<Nivel> findAll(Pageable pageable){
        Page<Nivel> result = nivelRepository.findAll(pageable);

        if(result.isEmpty())
            throw new NivelNotFoundException();
        return result;
    }

    //Crear Nivel.
    @Transactional
    public Nivel save(CreateNivelCMD nuevo){
        List<Nivel> listaNiveles = nivelRepository.findListaNivelesOrderedByOrden();

        int numOrden = nuevo.orden();
        int longitudListaNiveles = listaNiveles.size();

        int ultimoNumOrden = numOrden > longitudListaNiveles ? longitudListaNiveles + 1 : numOrden;

        listaNiveles.stream()
            .filter(niv -> niv.getOrden() >= ultimoNumOrden)
            .sorted((a, b) -> Integer.compare(b.getOrden(), a.getOrden()))
            .forEach(niv -> {
                niv.setOrden(niv.getOrden() + 1);
                nivelRepository.save(niv);
            }
        );

        return nivelRepository.save(Nivel.builder()
                .nombre(nuevo.nombre().trim())
                .color(nuevo.color().trim().toUpperCase())
                .orden(nuevo.orden())
                .build()
        );
    }

    //Editar Nivel (CON FLECHAS Y CAMPO CON NUMERITO MODIFICABLE).
    /*@Transactional
    public Nivel edit(EditNivelCMD editNivelCMD, Long id) {
        Nivel nivelActual = nivelRepository.findById(id).orElseThrow(() -> new NivelNotFoundException(id));

        //Alternativa de la validación "UniqueNombreNivelEdit".
        boolean nombreDuplicado = nivelRepository.existsByNombreIgnoreCaseAndIdNot(editNivelCMD.nombre().trim(), id);
        if (nombreDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un nivel registrado con ese nombre.");
        }

        //Alternativa de la validación "@Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "{nivel.color.pattern}")".
        String colorNormalizado = editNivelCMD.color().trim();
        if (!colorNormalizado.matches("^#([A-Fa-f0-9]{6})$")) {
            throw new IncorrectPatternException("El color de un nivel debe seguir la estructura de un código hexadecimal.");
        }

        //Alternativa de la validación "UniqueColorNivelEdit".
        boolean colorDuplicado = nivelRepository.existsByColorIgnoreCaseAndIdNot(editNivelCMD.color().trim(), id);
        if (colorDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un nivel registrado con ese color.");
        }

        List<Nivel> listaNiveles = nivelRepository.findListaNivelesOrderedByOrden();

        int nuevoOrden = editNivelCMD.orden();
        int maxOrden = listaNiveles.size();

        // Ajustar el orden a un rango válido
        if (nuevoOrden < 1) nuevoOrden = 1;
        if (nuevoOrden > maxOrden) nuevoOrden = maxOrden;

        // Si cambia el orden, reorganizamos la lista
        if (nivelActual.getOrden() != nuevoOrden) {
            for (Nivel n : listaNiveles) {
                if (n.getId().equals(nivelActual.getId())) continue;

                if (nuevoOrden < nivelActual.getOrden()) {
                    // El nivel sube (ej: de orden 4 a 2): empuja otros hacia abajo
                    if (n.getOrden() >= nuevoOrden && n.getOrden() < nivelActual.getOrden()) {
                        n.setOrden(n.getOrden() + 1);
                        nivelRepository.save(n);
                    }
                } else {
                    // El nivel baja (ej: de orden 2 a 4): empuja otros hacia arriba
                    if (n.getOrden() <= nuevoOrden && n.getOrden() > nivelActual.getOrden()) {
                        n.setOrden(n.getOrden() - 1);
                        nivelRepository.save(n);
                    }
                }
            }
            nivelActual.setOrden(nuevoOrden);
        }

        // Actualizar otros campos
        nivelActual.setNombre(editNivelCMD.nombre().trim());
        nivelActual.setColor(editNivelCMD.color().trim().toUpperCase());

        return nivelRepository.save(nivelActual);
    }*/

    //Editar Nivel (VERSIÓN FORMULARIO).
    @Transactional
    public Nivel edit(EditNivelCMD editNivelCMD, Long id){
        Nivel nivel = nivelRepository.findById(id).orElseThrow(() -> new NivelNotFoundException(id));

        //Alternativa de la validación "UniqueNombreNivelEdit".
        boolean nombreDuplicado = nivelRepository.existsByNombreIgnoreCaseAndIdNot(editNivelCMD.nombre().trim(), id);
        if (nombreDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un nivel registrado con ese nombre.");
        }

        //Alternativa de la validación "@Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "{nivel.color.pattern}")".
        String colorNormalizado = editNivelCMD.color().trim();
        if (!colorNormalizado.matches("^#([A-Fa-f0-9]{6})$")) {
            throw new IncorrectPatternException("El color de un nivel debe seguir la estructura de un código hexadecimal.");
        }

        //Alternativa de la validación "UniqueColorNivelEdit".
        boolean colorDuplicado = nivelRepository.existsByColorIgnoreCaseAndIdNot(editNivelCMD.color().trim(), id);
        if (colorDuplicado) {
            throw new DuplicatedAttributeException("Ya existe un nivel registrado con ese color.");
        }

        List<Nivel> listaNiveles = nivelRepository.findListaNivelesOrderedByOrden();

        int nivelActual = nivel.getOrden();
        int nivelSolicitado = editNivelCMD.orden();

        int nivelFinal = Math.min(nivelSolicitado, listaNiveles.size());

        if (nivelFinal < nivelActual) {
            listaNiveles.stream()
                .filter(niv -> niv.getOrden() >= nivelFinal && niv.getOrden() < nivelActual)
                .sorted((a, b) -> Integer.compare(b.getOrden(), a.getOrden()))
                .forEach(niv -> {
                    niv.setOrden(niv.getOrden() + 1);
                    nivelRepository.save(niv);
                }
            );
        } else if (nivelFinal > nivelActual) {
            listaNiveles.stream()
                .filter(niv -> niv.getOrden() <= nivelFinal && niv.getOrden() > nivelActual)
                .sorted(Comparator.comparingInt(Nivel::getOrden))
                .forEach(niv -> {
                    niv.setOrden(niv.getOrden() - 1);
                    nivelRepository.save(niv);
                });
        }

        nivel.setNombre(editNivelCMD.nombre().trim());
        nivel.setColor(editNivelCMD.color().trim().toUpperCase());
        nivel.setOrden(nivelFinal);

        return nivelRepository.save(nivel);
    }

    //Borrar Nivel.
    public void delete(Long id){
        Nivel nivel = nivelRepository.findById(id).orElseThrow(() -> new NivelNotFoundException(id));

        if(!nivel.getListaTalentos().isEmpty()){
            throw new EntityWithRelationshipsException("No se ha podido eliminar el nivel " +
                "con ID: " + nivel.getId() + " porque tiene talentos asociados.");
        }
        nivelRepository.delete(nivel);

        List<Nivel> listaNiveles = nivelRepository.findListaNivelesOrderedByOrden();

        listaNiveles.stream()
            .filter(niv -> niv.getOrden() > nivel.getOrden())
            .forEach(niv -> {
                niv.setOrden(niv.getOrden() - 1);
                nivelRepository.save(niv);
            }
        );
    }

}
