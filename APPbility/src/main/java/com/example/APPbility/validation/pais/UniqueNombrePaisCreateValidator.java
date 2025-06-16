package com.example.APPbility.validation.pais;

import com.example.APPbility.repository.PaisRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueNombrePaisCreateValidator implements ConstraintValidator<UniqueNombrePaisCreate, String> {

    @Autowired
    private PaisRepository paisRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.hasText(s)) {
            return true;
        }

        return !paisRepository.existsByNombreIgnoreCase(s.trim());
    }

}
