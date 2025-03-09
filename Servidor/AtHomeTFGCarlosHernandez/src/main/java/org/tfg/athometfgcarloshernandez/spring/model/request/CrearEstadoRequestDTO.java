package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrearEstadoRequestDTO {
    private String estado;
    private String color;
    private String idUsuario;
}
