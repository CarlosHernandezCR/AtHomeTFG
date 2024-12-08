package org.tfg.inhometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.domain.model.Usuario;
import org.tfg.inhometfgcarloshernandez.spring.model.UsuarioDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUsuariosResponseDTO {
    private List<UsuarioDTO> usuarios;
}
