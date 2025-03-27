package org.tfg.athometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.spring.model.CajonDTO;
import org.tfg.athometfgcarloshernandez.spring.model.MuebleDTO;
import org.tfg.athometfgcarloshernandez.spring.model.ProductoDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CargarProductosResponseDTO {
    private List<ProductoDTO> productos;
    private List<CajonDTO> cajones;
    private List<MuebleDTO> muebles;
    private int idPropietario;
}
