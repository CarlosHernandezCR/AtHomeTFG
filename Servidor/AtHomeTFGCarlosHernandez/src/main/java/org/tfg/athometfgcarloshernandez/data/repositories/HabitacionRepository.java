package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.athometfgcarloshernandez.data.model.HabitacionEntity;

import java.util.List;

@Repository
public interface HabitacionRepository  extends JpaRepository<HabitacionEntity, Integer> {
    List<HabitacionEntity> findByIdCasaEntity(CasaEntity idCasaEntity);
}
