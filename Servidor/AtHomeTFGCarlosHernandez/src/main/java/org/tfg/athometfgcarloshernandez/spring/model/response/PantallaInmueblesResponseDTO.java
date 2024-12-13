package org.tfg.athometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.athometfgcarloshernandez.spring.model.HabitacionDTO;
import org.tfg.athometfgcarloshernandez.spring.model.MuebleDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PantallaInmueblesResponseDTO {
    private List<HabitacionDTO> habitaciones;
    private List<MuebleDTO> muebles;
    private List<CajonDTO> cajones;
}
