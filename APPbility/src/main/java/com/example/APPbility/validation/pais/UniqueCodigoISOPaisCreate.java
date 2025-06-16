package com.example.APPbility.validation.pais;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCodigoISOPaisCreateValidator.class)
@Documented
public @interface UniqueCodigoISOPaisCreate {

    String message() default "Ya existe un pais registrado con ese código ISO.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
