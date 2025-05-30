/*package com.example.APPbility.validation.continente.edit;

import com.example.APPbility.dto.continente.EditContinenteCMD;
import com.example.APPbility.repository.ContinenteRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueNombreContinenteEditValidator implements ConstraintValidator<UniqueNombreContinenteEdit, EditContinenteCMD> {

    @Autowired
    private ContinenteRepository continenteRepository;

    @Override
    public boolean isValid(EditContinenteCMD dto, ConstraintValidatorContext constraintValidatorContext) {
        if (dto.nombre() == null || dto.nombre().trim().isEmpty()) {
            return true;
        }

        return !continenteRepository.existsByNombreIgnoreCaseAndIdNot(dto.nombre().trim(), dto.id());
    }

}*/
