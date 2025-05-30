package com.example.APPbility.validation.talento;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTituloTalentoValidator.class)
@Documented
public @interface UniqueTituloTalento {

    String message() default "Ya existe un talento registrado con ese título en tu perfil.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
