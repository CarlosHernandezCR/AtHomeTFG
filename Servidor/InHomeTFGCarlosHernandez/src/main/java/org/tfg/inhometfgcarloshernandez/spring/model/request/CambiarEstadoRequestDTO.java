package org.tfg.inhometfgcarloshernandez.spring.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CambiarEstadoRequestDTO {
    private String estado;
    private int idCasa;
    private int idUsuario;
}
