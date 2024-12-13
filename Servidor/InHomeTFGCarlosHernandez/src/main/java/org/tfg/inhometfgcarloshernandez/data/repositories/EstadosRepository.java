package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.EstadoEntity;

import java.util.Optional;

@Repository
public interface EstadosRepository extends JpaRepository<EstadoEntity, Integer> {

    Optional<EstadoEntity> findByDescripcion(String descripcion);
}