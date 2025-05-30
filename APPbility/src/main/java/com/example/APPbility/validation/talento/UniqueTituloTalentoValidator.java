package com.example.APPbility.validation.talento;

import com.example.APPbility.repository.TalentoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueTituloTalentoValidator implements ConstraintValidator<UniqueTituloTalento, String> {

    @Autowired
    private TalentoRepository talentoRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.hasText(s)){
            return true;
        }

        return false;
    }

}
