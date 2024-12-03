package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.EstadosUsuarioEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadosUsuariosRepository extends JpaRepository<EstadosUsuarioEntity, Integer> {
    @Query("SELECT eu.estadoEntity.descripcion FROM EstadosUsuarioEntity eu WHERE eu.usuarioEntity.id= :idUsuario")
    List<String> findEstadosUsuarioPorIdUsuario(Integer idUsuario);

    Optional<EstadosUsuarioEntity> findByUsuarioEntityId(int id);}
