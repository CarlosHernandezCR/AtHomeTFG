package org.tfg.athometfgcarloshernandez.spring.exception_mappers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tfg.athometfgcarloshernandez.domain.errores.*;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomedException.class)
    public ResponseEntity<String> handleBadUserException(CustomedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(YaVotadoException.class)
    public ResponseEntity<String> handleBadUserException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ConstantesError.YA_VOTADO);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleBadUserException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<String> handleBadUserException(TokenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ConstantesError.TOKEN_EXPIRADO);
    }
    @ExceptionHandler(ErrorLoginException.class)
    public ResponseEntity<String> handleBadUserException(ErrorLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ConstantesError.ERROR_INICIAR_SESION);
    }

}