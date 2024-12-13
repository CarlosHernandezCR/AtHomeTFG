package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.athometfgcarloshernandez.data.model.ViveEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViveRepository extends JpaRepository<ViveEntity, Integer> {

    Optional<ViveEntity> findByCasaEntityAndUsuarioEntity(CasaEntity casaEntity, UsuarioEntity usuarioEntity);

    List<ViveEntity> findByCasaEntity(CasaEntity casa);
}
