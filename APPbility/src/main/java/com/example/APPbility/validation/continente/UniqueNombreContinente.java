package com.example.APPbility.validation.continente;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNombreContinenteValidator.class)
@Documented
public @interface UniqueNombreContinente {

    String message() default "Ya existe un continente registrado con ese nombre.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
