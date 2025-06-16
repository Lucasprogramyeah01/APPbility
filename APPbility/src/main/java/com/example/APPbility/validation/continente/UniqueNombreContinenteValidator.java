package com.example.APPbility.validation.continente;

import com.example.APPbility.repository.ContinenteRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueNombreContinenteValidator implements ConstraintValidator<UniqueNombreContinente, String> {

    @Autowired
    private ContinenteRepository continenteRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.hasText(s)){
            return true;
        }

        return !continenteRepository.existsByNombreIgnoreCase(s.trim());
    }

}
