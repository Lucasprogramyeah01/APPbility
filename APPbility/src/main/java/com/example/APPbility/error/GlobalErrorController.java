package com.example.APPbility.error;

import com.example.APPbility.error.custom.*;
import com.example.APPbility.error.entity.ContinenteNotFoundException;
import com.example.APPbility.error.entity.NivelNotFoundException;
import com.example.APPbility.error.entity.PaisNotFoundException;
import com.example.APPbility.error.entity.TalentoNotFoundException;
import com.example.APPbility.user.error.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class GlobalErrorController  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorType = "Entidad no encontrada.";

        if (ex instanceof UserNotFoundException usuarioEx) {
            errorType = "Usuario no encontrado.";
        } else if (ex instanceof PaisNotFoundException paisEx) {
            errorType = "País no encontrado.";
        } else if (ex instanceof ContinenteNotFoundException continenteEx) {
            errorType = "Continente no encontrado.";
        } else if (ex instanceof NivelNotFoundException nivelEx) {
            errorType = "Nivel no encontrado.";
        } else if (ex instanceof TalentoNotFoundException talentoEx) {
            errorType = "Talento no encontrado.";
        }

        Map<String, Object> errorBody = Map.of(
                "error", errorType,
                "message", ex.getMessage(),
                "status", status.value()
        );

        return ResponseEntity.status(status).body(errorBody);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<Map<String, Object>> handleCustomValidationException(CustomValidationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorType = "Error de validación personalizada.";

        if (ex instanceof DuplicatedAttributeException) {
            errorType = "Atributo duplicado.";
        } else if (ex instanceof IncorrectSizeException) {
            errorType = "Tamaño incorrecto de campo.";
        } else if (ex instanceof IncorrectPatternException) {
            errorType = "Patrón no cumplido.";
        } else if (ex instanceof UnauthorizedAccessException) {
            errorType = "Permiso no concedido.";
        }

        Map<String, Object> errorBody = Map.of(
                "error", errorType,
                "message", ex.getMessage(),
                "status", status.value()
        );

        return ResponseEntity.status(status).body(errorBody);
    }

    @ExceptionHandler(EntityWithRelationshipsException.class)
    public ResponseEntity<String> EntityWithRelationshipsException(EntityWithRelationshipsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {
        ProblemDetail result = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        List<ApiValidationSubError> subErrors =
            ex.getConstraintViolations().stream()
                .map(ApiValidationSubError::from)
                .toList();

        result.setProperty("invalid-params", subErrors);

        return result;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail result = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación");

        List<ApiValidationSubError> subErrors =
            ex.getAllErrors().stream()
                .map(ApiValidationSubError::from)
                .toList();

        result.setProperty("invalid-params", subErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Builder
    record ApiValidationSubError(
            String object,
            String message,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String field,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            Object rejectedValue
    ){

        public ApiValidationSubError(String object, String message) {
            this(object, message, null, null);
        }

        public static ApiValidationSubError from(ObjectError error) {

            ApiValidationSubError result = null;

            if (error instanceof FieldError fieldError) {
                result = ApiValidationSubError.builder()
                        .object(fieldError.getObjectName())
                        .message(fieldError.getDefaultMessage())
                        .field(fieldError.getField())
                        .rejectedValue(fieldError.getRejectedValue())
                        .build();
            } else {
                result = ApiValidationSubError.builder()
                        .object(error.getObjectName())
                        .message(error.getDefaultMessage())
                        .build();
            }

            return result;
        }

        public static ApiValidationSubError from(ConstraintViolation v) {
            return ApiValidationSubError.builder()
                    .message(v.getMessage())
                    .rejectedValue(v.getInvalidValue())
                    .object(v.getRootBean().getClass().getSimpleName())
                    .field(
                        Optional.ofNullable(v.getPropertyPath())
                            .map(PathImpl.class::cast)
                            .map(PathImpl::getLeafNode)
                            .map(NodeImpl::asString)
                            .orElse("unknown")
                    )
                    .build();
        }

    }

}
