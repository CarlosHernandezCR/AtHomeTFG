package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.data.model.ViveEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.CasaRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.EstadosUsuariosRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.ViveRepository;
import org.tfg.inhometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.CasaMapper;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.inhometfgcarloshernandez.spring.common.utils.TokensTools;
import org.tfg.inhometfgcarloshernandez.spring.model.CasaDetallesDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.UsuarioCasaDTO;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CasaServicios {

    private final UsuarioRepository usuarioRepository;
    private final CasaRepository casaRepository;
    private final EstadosUsuariosRepository estadosUsuariosRepository;
    private final ViveRepository viveRepository;
    private final CasaMapper casaMapper;
    private final TokensTools tokensTools;

    public PantallaEstadosResponseDTO getDatosPantallaEstados(String idUsuario, String idCasa, String token) {
        UsuarioEntity usuario = usuarioRepository.findById(Integer.parseInt(idUsuario))
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + Integer.parseInt(idUsuario)));

        CasaEntity casa = casaRepository.findById(Integer.parseInt(idCasa))
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_CASA_NO_ENCONTRADA + usuario.getId()));

        List<ViveEntity> viveEntities = viveRepository.findByCasaEntity(casa);

        List<UsuarioEntity> usuariosCasa = viveEntities.stream()
                .map(ViveEntity::getUsuarioEntity)
                .filter(usuarioEntity -> !Objects.equals(usuarioEntity.getId(), usuario.getId()))
                .toList();

        String estadoActual = viveEntities.stream()
                .filter(viveEntity -> Objects.equals(viveEntity.getUsuarioEntity().getId(), usuario.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_VIVE_NO_ENCONTRADO + usuario.getId() + "-" + casa.getId()))
                .getEstado();

        List<String> estadosDisponibles = estadosUsuariosRepository.findEstadosUsuarioPorIdUsuario(Integer.parseInt(idUsuario));
        return mapearDatosUsuario(estadoActual,casa,usuariosCasa,estadosDisponibles,token);
    }

    private PantallaEstadosResponseDTO mapearDatosUsuario(String estadoActual, CasaEntity casa, List<UsuarioEntity> usuariosCasa, List<String> estadosDisponibles, String token) {
        return PantallaEstadosResponseDTO.builder()
                .estado(estadoActual)
                .idCasa(casa.getId())
                .nombreCasa(casa.getNombre())
                .direccion(casa.getDireccion())
                .usuariosCasa(convertirAUsuariosCasa(usuariosCasa, viveRepository.findByCasaEntity(casa)))
                .estadosDisponibles(estadosDisponibles)
                .accessToken(tokensTools.actualizarAccessTokenConCasa(token, casa.getId()))
                .refreshToken(tokensTools.actualizarRefreshTokenConCasa(token, casa.getId()))
                .build();
    }

    private List<UsuarioCasaDTO> convertirAUsuariosCasa(List<UsuarioEntity> usuariosCasa, List<ViveEntity> viveEntities) {
        return usuariosCasa.stream()
                .map(usuario -> {
                    String estado = viveEntities.stream()
                            .filter(viveEntity -> viveEntity.getUsuarioEntity().getId().equals(usuario.getId()))
                            .findFirst()
                            .map(ViveEntity::getEstado)
                            .orElse("Estado no encontrado");
                    return new UsuarioCasaDTO(usuario.getNombre(), estado);
                })
                .toList();
    }

    public ResponseEntity<Void> cambiarEstado(String estado, int idUsuario, int idCasa) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + idUsuario));
        CasaEntity casa = casaRepository.findById(idCasa)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_CASA_NO_ENCONTRADA + idCasa));
        ViveEntity viveEntity = viveRepository.findByCasaEntityAndUsuarioEntity(casa,usuario)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_VIVE_NO_ENCONTRADO + idUsuario + "-" + idCasa));
        viveEntity.setEstado(estado);
        viveRepository.save(viveEntity);
        return ResponseEntity.ok().build();
    }

    public List<CasaDetallesDTO> getCasas(String idUsuario) {
        return casaRepository.findCasasPorUsuarioId(Integer.parseInt(idUsuario))
                .stream()
                .map(casaMapper::casaEntityToCasaDetallesDTO)
                .toList();
    }
}
