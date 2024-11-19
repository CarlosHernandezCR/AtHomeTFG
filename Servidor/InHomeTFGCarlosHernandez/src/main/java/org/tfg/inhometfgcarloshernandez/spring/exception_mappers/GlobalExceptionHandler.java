package org.tfg.inhometfgcarloshernandez.spring.exception_mappers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tfg.inhometfgcarloshernandez.domain.errores.CustomedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomedException.class)
    public ResponseEntity<String> handleBadUserException(CustomedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}