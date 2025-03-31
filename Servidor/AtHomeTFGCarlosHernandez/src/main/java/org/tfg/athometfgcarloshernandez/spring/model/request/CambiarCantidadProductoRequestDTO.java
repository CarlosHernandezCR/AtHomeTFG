package org.tfg.athometfgcarloshernandez.spring.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CambiarCantidadProductoRequestDTO {
    private String idProducto;
    private Integer cantidad;

}
