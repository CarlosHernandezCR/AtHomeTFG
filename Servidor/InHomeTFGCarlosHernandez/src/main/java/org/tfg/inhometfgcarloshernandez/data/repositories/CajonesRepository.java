package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.CajonEntity;

import java.util.List;

@Repository
public interface CajonesRepository  extends JpaRepository<CajonEntity, Integer> {

    @Query("SELECT c FROM CajonEntity c WHERE c.muebleEntity.nombre = :nombreMueble")
    List<CajonEntity> findCajonesByNombreMueble(@Param(Constantes.NOMBREMUEBLE) String nombreMueble);
}