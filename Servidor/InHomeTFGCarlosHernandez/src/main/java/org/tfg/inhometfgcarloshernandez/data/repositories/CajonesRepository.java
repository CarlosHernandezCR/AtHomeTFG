package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.CajonEntity;

import java.util.List;

@Repository
public interface CajonesRepository  extends JpaRepository<CajonEntity, Integer> {
    List<CajonEntity> findCajonesByMuebleEntityId(Integer id);
}
