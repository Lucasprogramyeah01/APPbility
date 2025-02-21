package com.example.APPbility.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalErrorController  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleEntityNotFound(RuntimeException ex){
        ProblemDetail result = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        result.setTitle("Entidad no encontrada.");
        result.setProperty("author", "Lucas Falla Urtiaga");

        return result;
    }

}
