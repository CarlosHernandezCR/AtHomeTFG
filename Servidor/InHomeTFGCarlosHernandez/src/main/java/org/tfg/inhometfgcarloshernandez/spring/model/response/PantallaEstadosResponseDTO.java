package org.tfg.inhometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PantallaEstadosResponseDTO {
    private String estado;
    private String nombreCasa;
    private String direccion;
    private List<UsuarioCasaDTO> usuariosCasa;
}