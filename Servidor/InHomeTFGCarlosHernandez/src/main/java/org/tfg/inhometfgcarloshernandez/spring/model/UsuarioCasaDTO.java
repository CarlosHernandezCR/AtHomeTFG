package org.tfg.inhometfgcarloshernandez.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCasaDTO {
    private String nombre;
    private String estado;
    private String colorEstado;
    private String colorUsuario;
}
