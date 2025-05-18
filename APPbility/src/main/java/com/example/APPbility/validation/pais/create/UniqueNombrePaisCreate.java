package com.example.APPbility.validation.pais.create;

import com.example.APPbility.validation.continente.edit.UniqueNombreContinenteEditValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNombrePaisCreateValidator.class)
@Documented
public @interface UniqueNombrePaisCreate {

    String message() default "Ya existe un pais registrado con ese nombre.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
