package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.athometfgcarloshernandez.data.model.MuebleEntity;

import java.util.List;

@Repository
public interface CajonesRepository  extends JpaRepository<CajonEntity, Integer> {

    List<CajonEntity> findByMuebleEntity(MuebleEntity muebleEntity);
}