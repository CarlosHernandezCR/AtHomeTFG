package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CasaRepository extends JpaRepository<CasaEntity, Integer> {

    @Query("SELECT u FROM UsuarioEntity u JOIN ViveEntity v ON u.id = v.usuarioEntity.id WHERE v.casaEntity.id = :idCasa")
    List<UsuarioEntity> findUsuariosPorCasaId(Integer idCasa);

    @Query("SELECT c FROM CasaEntity c JOIN ViveEntity v ON c.id = v.casaEntity.id WHERE v.usuarioEntity.id = :idUsuario")
    List<CasaEntity> findCasasPorUsuarioId(Integer idUsuario);

    Optional<CasaEntity> findByCodigoInvitacion(String codigoInvitacion);
}
