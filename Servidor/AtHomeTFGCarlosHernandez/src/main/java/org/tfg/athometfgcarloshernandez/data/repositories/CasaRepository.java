package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.CasaEntity;

import java.util.List;
import java.util.Optional;

import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_CASAS_POR_USUARIO_ID;

@Repository
public interface CasaRepository extends JpaRepository<CasaEntity, Integer> {

    @Query(FIND_CASAS_POR_USUARIO_ID)
    List<CasaEntity> findCasasPorUsuarioId(Integer idUsuario);

    Optional<CasaEntity> findByCodigoInvitacion(String codigoInvitacion);
}
