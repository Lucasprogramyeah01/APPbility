/*package com.example.APPbility.validation.pais.edit;

import com.example.APPbility.dto.pais.EditPaisCMD;
import com.example.APPbility.repository.PaisRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueNombrePaisEditValidator implements ConstraintValidator<UniqueNombrePaisEdit, EditPaisCMD> {

    @Autowired
    private PaisRepository paisRepository;

    @Override
    public boolean isValid(EditPaisCMD dto, ConstraintValidatorContext constraintValidatorContext) {
        if (dto.nombre() == null || dto.nombre().trim().isEmpty()) {
            return true;
        }

        return !paisRepository.existsByNombreIgnoreCaseAndIdNot(dto.nombre().trim(), dto.id());
    }

}*/
