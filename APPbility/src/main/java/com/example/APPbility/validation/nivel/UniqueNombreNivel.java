package com.example.APPbility.validation.nivel;

import com.example.APPbility.validation.continente.create.UniqueNombreContinenteValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNombreNivelValidator.class)
@Documented
public @interface UniqueNombreNivel {

    String message() default "Ya existe un nivel registrado con ese nombre.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
