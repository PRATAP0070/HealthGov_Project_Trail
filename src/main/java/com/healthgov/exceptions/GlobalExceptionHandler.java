package com.healthgov.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

   //Handle custom exception
    @ExceptionHandler(MedicalResearchException.class)
    public ResponseEntity<List<String>> handleMedicalException(MedicalResearchException ex) {

        return new ResponseEntity<>(
                List.of(
                        "Error: " + ex.getStatus().name(),
                        "Message: " + ex.getMessage()
                ),
                ex.getStatus()
        );
    }

    // Handle validation errors (@Valid, @NotNull, @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return new ResponseEntity<>(
                List.of(
                        "Error: BAD_REQUEST",
                        "Message: " + message
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    //Handle unexpected exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<String>> handleOther(Exception ex) {

        return new ResponseEntity<>(
                List.of(
                        "Error: INTERNAL_SERVER_ERROR",
                        "Message: Unexpected error occurred"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}