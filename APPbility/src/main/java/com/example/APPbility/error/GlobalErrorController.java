package com.example.APPbility.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalErrorController  extends ResponseEntityExceptionHandler {

    /*@ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleEntityNotFound(RuntimeException ex){
        ProblemDetail result = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        result.setTitle("Entidad no encontrada.");
        result.setProperty("author", "Lucas Falla Urtiaga");

        return result;
    }*/

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorType = "Recurso no encontrado";

        // Personalizar mensajes usando pattern matching
        if (ex instanceof TagNotFoundException trabajadorEx) {
            errorType = "Tag no encontrado";
        } /*else if (ex instanceof UserNotFoundException userEx) {
            errorType = "Usuario no encontrado";
        }*/

        Map<String, Object> errorBody = Map.of(
                "error", errorType,
                "message", ex.getMessage(),
                "status", status.value()
        );

        return ResponseEntity.status(status).body(errorBody);
    }

}
