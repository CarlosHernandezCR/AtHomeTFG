package org.tfg.athometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.athometfgcarloshernandez.data.model.*;
import org.tfg.athometfgcarloshernandez.data.repositories.*;
import org.tfg.athometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.CajonMappers;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.HabitacionMapper;
import org.tfg.athometfgcarloshernandez.domain.model.mappers.MuebleMapper;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarCajonRequestDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarHabitacionRequestDTO;
import org.tfg.athometfgcarloshernandez.spring.model.request.AgregarMuebleRequestDTO;
import org.tfg.athometfgcarloshernandez.spring.model.response.PantallaInmueblesResponseDTO;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InmuebleServicios {
    private final HabitacionRepository habitacionRepository;
    private final MueblesRepository mueblesRepository;
    private final CajonesRepository cajonesRepository;
    private final UsuarioRepository usuarioRepository;
    private final CajonMappers cajonMappers;
    private final MuebleMapper muebleMapper;
    private final HabitacionMapper habitacionMapper;
    private final CasaRepository casaRepository;

    public PantallaInmueblesResponseDTO getInmuebles(int idCasa, String idHabitacion, String idMueble) {
        CasaEntity casaEntity = casaRepository.findById(idCasa).orElseThrow(() -> new NotFoundException(ConstantesError.CASA_NO_ENCONTRADA));
        List<HabitacionEntity> habitaciones = habitacionRepository.findByIdCasaEntity(casaEntity);
        List<MuebleEntity> muebles = Collections.emptyList();
        List<CajonEntity> cajones = Collections.emptyList();
        HabitacionEntity habitacion;
        MuebleEntity mueble;
        if (!habitaciones.isEmpty()) {
            if (idHabitacion != null) {
                habitacion = habitaciones.stream().filter(h -> h.getId().equals(Integer.parseInt(idHabitacion))).findFirst().orElse(null);
            } else {
                habitacion = habitaciones.get(0);
            }
            muebles = mueblesRepository.findByIdHabitacionEntity(habitacion);
            if (!muebles.isEmpty()){
                if (idMueble != null) {
                    mueble = muebles.stream().filter(m -> m.getId().equals(Integer.parseInt(idMueble))).findFirst().orElse(null);
                } else {
                    mueble = muebles.get(0);
                }
            cajones = cajonesRepository.findByMuebleEntity(mueble);
            }
        }
        return mapearDatosInmueble(habitaciones, muebles, cajones);
    }

    public PantallaInmueblesResponseDTO mapearDatosInmueble(List<HabitacionEntity> habitaciones, List<MuebleEntity> muebles, List<CajonEntity> cajones) {
        return new PantallaInmueblesResponseDTO(
                habitaciones.stream().map(habitacionMapper::toHabitacionDTO).toList(),
                muebles.stream().map(muebleMapper::toMuebleDTO).toList(),
                cajones.stream().map(cajonMappers::cajonEntityToCajonDTO).toList()
        );
    }

    public void agregarHabitacion(AgregarHabitacionRequestDTO agregarHabitacionRequestDTO) {
        CasaEntity casa = casaRepository.findById(agregarHabitacionRequestDTO.getIdCasa())
                .orElseThrow(() -> new NotFoundException(ConstantesError.CASA_NO_ENCONTRADA));
        HabitacionEntity habitacionEntity = new HabitacionEntity();
        habitacionEntity.setNombre(agregarHabitacionRequestDTO.getNombre());
        habitacionEntity.setIdCasaEntity(casa);
        habitacionRepository.save(habitacionEntity);
    }

    public void agregarMueble(AgregarMuebleRequestDTO agregarMuebleRequestDTO) {
        HabitacionEntity habitacion = habitacionRepository.findById(agregarMuebleRequestDTO.getIdHabitacion())
                .orElseThrow(() -> new NotFoundException(ConstantesError.HABITACION_NO_ENCONTRADA));
        MuebleEntity muebleEntity = new MuebleEntity();
        muebleEntity.setNombre(agregarMuebleRequestDTO.getNombre());
        muebleEntity.setIdHabitacionEntity(habitacion);
        mueblesRepository.save(muebleEntity);
    }


    public void agregarCajon(AgregarCajonRequestDTO request) {
        UsuarioEntity usuario = usuarioRepository.findById(request.getIdPropietario())
                .orElseThrow(() -> new NotFoundException(ConstantesError.USUARIO_NO_ENCONTRADO));
        MuebleEntity mueble = mueblesRepository.findById(request.getIdMueble())
                .orElseThrow(() -> new NotFoundException(ConstantesError.MUEBLE_NO_ENCONTRADO));

        CajonEntity cajonEntity = new CajonEntity();
        cajonEntity.setNombre(request.getNombre());
        cajonEntity.setPropietario(usuario);
        cajonEntity.setMuebleEntity(mueble);

        cajonesRepository.save(cajonEntity);
    }
}