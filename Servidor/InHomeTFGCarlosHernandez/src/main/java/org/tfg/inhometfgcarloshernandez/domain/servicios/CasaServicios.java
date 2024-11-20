package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.CasaRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.UsuarioCasaDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CasaServicios {

    private final UsuarioRepository usuarioRepository;
    private final CasaRepository casaRepository;

    public PantallaEstadosResponseDTO getDatosPrimeraPantalla(Integer id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + id));

        CasaEntity casa = casaRepository.findByIdUsuario(usuario.getId())
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_CASA_NO_ENCONTRADA + usuario.getId()));

        List<UsuarioEntity> usuariosCasa = casaRepository.findUsuariosPorCasaId(casa.getId());

        return mapearDatosUsuario(usuario, casa, usuariosCasa);
    }

    private PantallaEstadosResponseDTO mapearDatosUsuario(UsuarioEntity usuario, CasaEntity casa, List<UsuarioEntity> usuariosCasa) {
        return new PantallaEstadosResponseDTO(
                usuario.getEstado(),
                casa.getNombre(),
                casa.getDireccion(),
                usuariosCasa.stream()
                        .filter(usuarioLogado -> !usuarioLogado.getId().equals(usuario.getId()))
                        .map(u -> new UsuarioCasaDTO(u.getNombre(), u.getEstado()))
                        .toList()
        );
    }

}
