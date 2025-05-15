package com.example.APPbility.validation.tagPRUEBA;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNameTagValidator.class)
@Documented
public @interface UniqueNameTag {

    String message() default "Ya existe un Tag con ese nombre.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
