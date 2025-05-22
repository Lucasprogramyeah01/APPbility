package com.example.APPbility.validation.nivel;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueColorNivelValidator.class)
@Documented
public @interface UniqueColorNivel {

    String message() default "Ya existe un nivel registrado con ese color.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
