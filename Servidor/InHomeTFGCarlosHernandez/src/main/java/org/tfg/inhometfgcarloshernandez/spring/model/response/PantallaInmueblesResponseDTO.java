package org.tfg.inhometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.MuebleDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PantallaInmueblesResponseDTO {
    private List<String> habitaciones;
    private List<MuebleDTO> muebles;
    private List<CajonDTO> cajones;
}
