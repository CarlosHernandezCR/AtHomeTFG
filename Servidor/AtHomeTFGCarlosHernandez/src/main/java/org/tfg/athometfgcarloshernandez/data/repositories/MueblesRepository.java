package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.HabitacionEntity;
import org.tfg.athometfgcarloshernandez.data.model.MuebleEntity;

import java.util.List;

@Repository
public interface MueblesRepository  extends JpaRepository<MuebleEntity, Integer> {

    List<MuebleEntity> findByIdHabitacionEntity(HabitacionEntity habitacionEntity);
}
