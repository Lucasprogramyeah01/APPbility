package com.example.APPbility.validation.pais.create;

import com.example.APPbility.repository.PaisRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueCodigoISOPaisCreateValidator implements ConstraintValidator<UniqueCodigoISOPaisCreate, String> {

    @Autowired
    private PaisRepository paisRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.hasText(s)) {
            return true;
        }

        return !paisRepository.existsByCodigoISOIgnoreCase(s.trim());
    }

}
