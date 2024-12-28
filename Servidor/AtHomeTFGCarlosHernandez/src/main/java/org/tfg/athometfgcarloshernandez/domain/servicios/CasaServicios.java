package org.tfg.athometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.athometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.athometfgcarloshernandez.data.model.EstadoEntity;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.athometfgcarloshernandez.data.model.ViveEntity;
import org.tfg.athometfgcarloshernandez.data.repositories.*;
import org.tfg.athometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.CasaMapper;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.athometfgcarloshernandez.spring.common.utils.Security;
import org.tfg.athometfgcarloshernandez.spring.common.utils.TokensTools;
import org.tfg.athometfgcarloshernandez.spring.model.CasaDetallesDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;
import org.tfg.athometfgcarloshernandez.spring.model.UsuarioCasaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CasaServicios {

    private final UsuarioRepository usuarioRepository;
    private final CasaRepository casaRepository;
    private final EstadosUsuariosRepository estadosUsuariosRepository;
    private final ViveRepository viveRepository;
    private final EstadosRepository estadosRepository;
    private final CasaMapper casaMapper;
    private final TokensTools tokensTools;
    private final Security security;

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
        String colorEstado = estadosRepository.findByDescripcion(estadoActual)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_ESTADO_NO_ENCONTRADO + estadoActual))
                .getColor();
        List<String> estadosDisponibles = estadosUsuariosRepository.findEstadosUsuarioPorIdUsuario(Integer.parseInt(idUsuario));
        return mapearDatosUsuario(estadoActual,colorEstado,usuario.getColor(),casa,usuariosCasa,estadosDisponibles,token);
    }

    private PantallaEstadosResponseDTO mapearDatosUsuario(String estadoActual,String colorEstado, String color,CasaEntity casa, List<UsuarioEntity> usuariosCasa, List<String> estadosDisponibles, String token) {
        return PantallaEstadosResponseDTO.builder()
                .estado(estadoActual)
                .colorEstado(colorEstado)
                .colorUsuario(color)
                .idCasa(casa.getId())
                .nombreCasa(casa.getNombre())
                .direccion(casa.getDireccion())
                .codigoInvitacion(casa.getCodigoInvitacion())
                .usuariosCasa(convertirAUsuariosCasa(usuariosCasa, viveRepository.findByCasaEntity(casa)))
                .estadosDisponibles(estadosDisponibles)
                .accessToken(tokensTools.actualizarAccessTokenConCasa(token, casa.getId()))
                .refreshToken(tokensTools.actualizarRefreshTokenConCasa(token, casa.getId()))
                .build();
    }

    private List<UsuarioCasaDTO> convertirAUsuariosCasa(List<UsuarioEntity> usuariosCasa, List<ViveEntity> viveEntities) {
        List<EstadoEntity> estados = new ArrayList<>();
        for(ViveEntity viveEntity : viveEntities){
            estados.add(estadosRepository.findByDescripcion(viveEntity.getEstado())
                    .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_ESTADO_NO_ENCONTRADO + viveEntity.getEstado())));
        }
        List<UsuarioCasaDTO> usuarios = new ArrayList<>();
        for(UsuarioEntity usuario : usuariosCasa){
            for(ViveEntity viveEntity : viveEntities){
                if(Objects.equals(viveEntity.getUsuarioEntity().getId(), usuario.getId())){
                    usuarios.add(new UsuarioCasaDTO(usuario.getNombre(),viveEntity.getEstado(),estados.stream()
                            .filter(estadoEntity -> Objects.equals(estadoEntity.getDescripcion(), viveEntity.getEstado()))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_ESTADO_NO_ENCONTRADO + viveEntity.getEstado()))
                            .getColor(),usuario.getColor()));
                }
            }
        }
        return usuarios;
    }

    public String cambiarEstado(String estado, int idUsuario, int idCasa) {
        EstadoEntity estadoEntity = estadosRepository.findByDescripcion(estado)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_ESTADO_NO_ENCONTRADO + estado));
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + idUsuario));
        CasaEntity casa = casaRepository.findById(idCasa)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_CASA_NO_ENCONTRADA + idCasa));
        ViveEntity viveEntity = viveRepository.findByCasaEntityAndUsuarioEntity(casa,usuario)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_VIVE_NO_ENCONTRADO + idUsuario + "-" + idCasa));
        viveEntity.setEstado(estado);
        viveRepository.save(viveEntity);
        return estadoEntity.getColor();
    }

    public List<CasaDetallesDTO> getCasas(String idUsuario) {
        return casaRepository.findCasasPorUsuarioId(Integer.parseInt(idUsuario))
                .stream()
                .map(casaMapper::casaEntityToCasaDetallesDTO)
                .toList();
    }

    public void agregarCasa(String idUsuario, String nombre, String direccion, String codigoPostal) {
        String codigoInvitacion = security.generarCodigoSeguro();
        CasaEntity casaEntity = new CasaEntity(0, nombre, direccion, codigoPostal,codigoInvitacion);
        casaEntity=casaRepository.save(casaEntity);
        UsuarioEntity usuarioEntity = usuarioRepository.findById(Integer.parseInt(idUsuario))
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + Integer.parseInt(idUsuario)));
        viveRepository.save(new ViveEntity(0,ConstantesServer.ESTADO_PREDETERMINADO2,usuarioEntity, casaEntity));
    }

    public void unirseCasa(String idUsuario, String codigoInvitacion) {
        CasaEntity casa = casaRepository.findByCodigoInvitacion(codigoInvitacion)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_CASA_NO_ENCONTRADA + codigoInvitacion));
        UsuarioEntity usuario = usuarioRepository.findById(Integer.parseInt(idUsuario))
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + Integer.parseInt(idUsuario)));
        viveRepository.save(new ViveEntity(0, ConstantesServer.ESTADO_PREDETERMINADO2,usuario,casa));
    }
}
