package org.tfg.athometfgcarloshernandez.domain.errores;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message) {
        super(message);
    }
}