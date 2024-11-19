package org.tfg.inhometfgcarloshernandez.domain.errores;

public class TokenException extends RuntimeException{
    public TokenException(String message) {
        super(message);
    }
}