package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.inhometfgcarloshernandez.data.model.HabitacionEntity;
import org.tfg.inhometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.CajonesRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.HabitacionRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.MueblesRepository;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.CajonMappers;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.MuebleMapper;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaInmueblesResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InmuebleServicios {
    private final HabitacionRepository habitacionRepository;
    private final MueblesRepository mueblesRepository;
    private final CajonesRepository cajonesRepository;
    private final CajonMappers cajonMappers;
    private final MuebleMapper muebleMapper;

    public PantallaInmueblesResponseDTO getInmuebles(int idCasa, String nombreHabitacion, Integer idMueble) {
        List<HabitacionEntity> habitaciones = habitacionRepository.findByIdCasaEntityId(idCasa);

        List<MuebleEntity> muebles;
        List<CajonEntity> cajones;

        if (nombreHabitacion == null) {
            muebles = mueblesRepository.findByNombreHabitacion(habitaciones.get(0).getNombre());
            cajones = cajonesRepository.findCajonesByMuebleEntityId(muebles.get(0).getId());
        } else if (idMueble == null) {
            muebles = mueblesRepository.findByNombreHabitacion(nombreHabitacion);
            cajones = cajonesRepository.findCajonesByMuebleEntityId(muebles.get(0).getId());
        } else {
            muebles = mueblesRepository.findByNombreHabitacion(nombreHabitacion);
            cajones = cajonesRepository.findCajonesByMuebleEntityId(idMueble);
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
}
