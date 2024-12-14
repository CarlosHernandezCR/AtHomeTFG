package org.tfg.athometfgcarloshernandez.domain.errores;

public class ErrorLoginException extends RuntimeException{
    public ErrorLoginException(String message) {
        super(message);
    }
}