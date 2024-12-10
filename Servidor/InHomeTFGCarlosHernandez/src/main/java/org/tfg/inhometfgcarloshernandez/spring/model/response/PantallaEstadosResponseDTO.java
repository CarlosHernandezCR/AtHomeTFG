package org.tfg.inhometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PantallaEstadosResponseDTO {
    private String estado;
    private Integer idCasa;
    private String nombreCasa;
    private String direccion;
    private List<UsuarioCasaDTO> usuariosCasa;
    private List<String> estadosDisponibles;
}
