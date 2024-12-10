package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.inhometfgcarloshernandez.data.model.HabitacionEntity;
import org.tfg.inhometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.CajonesRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.HabitacionRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.MueblesRepository;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.CajonMappers;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.HabitacionMapper;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.MuebleMapper;
import org.tfg.inhometfgcarloshernandez.spring.model.request.AgregarCajonRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.AgregarHabitacionRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.AgregarMuebleRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaInmueblesResponseDTO;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InmuebleServicios {
    private final HabitacionRepository habitacionRepository;
    private final MueblesRepository mueblesRepository;
    private final CajonesRepository cajonesRepository;
    private final CajonMappers cajonMappers;
    private final MuebleMapper muebleMapper;
    private final HabitacionMapper habitacionMapper;

    public PantallaInmueblesResponseDTO getInmuebles(int idCasa, String nombreHabitacion, String mueble) {
        List<HabitacionEntity> habitaciones = habitacionRepository.findByIdCasaEntityId(idCasa);
        List<MuebleEntity> muebles = Collections.emptyList();
        List<CajonEntity> cajones = Collections.emptyList();

        if (!habitaciones.isEmpty()) {
            if (nombreHabitacion == null) {
                muebles = mueblesRepository.findByNombreHabitacion(habitaciones.get(0).getNombre());
                if (!muebles.isEmpty()) {
                    cajones = cajonesRepository.findCajonesByNombreMueble(muebles.get(0).getNombre());
                }
            } else if (mueble == null) {
                muebles = mueblesRepository.findByNombreHabitacion(nombreHabitacion);
                if (!muebles.isEmpty()) {
                    cajones = cajonesRepository.findCajonesByNombreMueble(muebles.get(0).getNombre());
                }
            } else {
                muebles = mueblesRepository.findByNombreHabitacion(nombreHabitacion);
                cajones = cajonesRepository.findCajonesByNombreMueble(mueble);
            }
        }

        return mapearDatosInmueble(habitaciones, muebles, cajones);
    }

    public PantallaInmueblesResponseDTO mapearDatosInmueble(List<HabitacionEntity> habitaciones, List<MuebleEntity> muebles, List<CajonEntity> cajones){
        return new PantallaInmueblesResponseDTO(
                habitaciones.stream().map(HabitacionEntity::getNombre).toList(),
                muebles.stream().map(muebleMapper::toMuebleDTO).toList(),
                cajones.stream().map(cajonMappers::cajonEntityToCajonDTO).toList()
        );
    }

    public void agregarHabitacion(AgregarHabitacionRequestDTO agregarHabitacionRequestDTO) {
        habitacionRepository.save(habitacionMapper.toHabitacionEntity(agregarHabitacionRequestDTO));
    }

    public void agregarMueble(AgregarMuebleRequestDTO agregarMuebleRequestDTO) {
        mueblesRepository.save(muebleMapper.toMuebleEntity(agregarMuebleRequestDTO));
    }


    public void agregarCajon(AgregarCajonRequestDTO request) {
        MuebleEntity muebleEntity = mueblesRepository.findByNombre(request.getNombreMueble());
        if (muebleEntity == null) {
            muebleEntity = new MuebleEntity();
            muebleEntity.setNombre(request.getNombreMueble());
            muebleEntity.setNombreHabitacion(request.getNombreHabitacion());
            muebleEntity = mueblesRepository.save(muebleEntity);
        }

        CajonEntity cajonEntity = new CajonEntity();
        cajonEntity.setNombre(request.getNombre());
        cajonEntity.setPropietario(new UsuarioEntity(request.getIdPropietario()));
        cajonEntity.setMuebleEntity(muebleEntity);

        cajonesRepository.save(cajonEntity);
    }
}