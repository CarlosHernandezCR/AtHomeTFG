package org.tfg.athometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.spring.model.UsuarioDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUsuariosResponseDTO {
    private List<UsuarioDTO> usuarios;
}
