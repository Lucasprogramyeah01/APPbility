package com.example.APPbility.validation.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class UserAgeOfMajorityValidator implements ConstraintValidator<UserAgeOfMajority, LocalDate> {

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        if (fechaNacimiento == null) {
            return false;
        }
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());
        return periodo.getYears() >= 18;
    }

}
