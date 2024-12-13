package org.tfg.inhometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistroRequestDTO {
    private String nombre;
    private String password;
    private String correo;
    private String telefono;
    private String color;
}
