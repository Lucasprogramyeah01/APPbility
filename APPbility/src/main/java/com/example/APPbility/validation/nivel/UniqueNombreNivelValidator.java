package com.example.APPbility.validation.nivel;

import com.example.APPbility.repository.NivelRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueNombreNivelValidator implements ConstraintValidator<UniqueNombreNivel, String> {

    @Autowired
    private NivelRepository nivelRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.hasText(s)){
            return true;
        }

        return !nivelRepository.existsByNombreIgnoreCase(s.trim());
    }

}
