package com.example.APPbility.validation.pais.edit;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCodigoISOPaisEditValidator.class)
@Documented
public @interface UniqueCodigoISOPaisEdit {

    String message() default "Ya existe un pais registrado con ese código ISO.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
