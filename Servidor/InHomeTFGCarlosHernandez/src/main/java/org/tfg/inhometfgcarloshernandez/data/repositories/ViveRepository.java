package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.ViveEntity;

import java.util.Optional;

@Repository
public interface ViveRepository {
    Optional<ViveEntity> findByUsuarioId(Integer id_usuario);
}
