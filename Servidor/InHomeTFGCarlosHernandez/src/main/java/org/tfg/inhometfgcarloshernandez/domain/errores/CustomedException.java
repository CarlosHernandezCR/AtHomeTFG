package org.tfg.inhometfgcarloshernandez.domain.errores;

public class CustomedException extends RuntimeException{
    public CustomedException(String message) {
        super(message);
    }
}