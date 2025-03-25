package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.AlmacenaEntity;
import org.tfg.athometfgcarloshernandez.data.model.CajonEntity;

import java.util.List;

@Repository
public interface AlmacenaRepository extends JpaRepository<AlmacenaEntity, Integer> {

    List<AlmacenaEntity> findByIdCajon(CajonEntity cajon);
}