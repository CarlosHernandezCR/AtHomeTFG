package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgregarCasaRequestDTO {
    private String idUsuario;
    private String nombre;
    private String direccion;
    private String codigoPostal;
}
