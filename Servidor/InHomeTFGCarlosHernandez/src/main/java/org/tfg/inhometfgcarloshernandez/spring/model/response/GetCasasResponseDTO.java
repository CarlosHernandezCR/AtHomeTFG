package org.tfg.inhometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.spring.model.CasaDetallesDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCasasResponseDTO {
    private List<CasaDetallesDTO> casas;
}
