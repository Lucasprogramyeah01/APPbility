package com.example.APPbility.validation.user;

import com.example.APPbility.validation.nivel.UniqueColorNivelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserAgeOfMajorityValidator.class)
@Documented
public @interface UserAgeOfMajority {

    String message() default "Se deben tener más de 18 años para crearse una cuenta.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
