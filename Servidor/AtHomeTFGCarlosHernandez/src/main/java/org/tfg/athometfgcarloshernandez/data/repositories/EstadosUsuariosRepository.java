package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.EstadosUsuarioEntity;

import java.util.List;

import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_ESTADOS_USUARIO_POR_ID_USUARIO;

@Repository
public interface EstadosUsuariosRepository extends JpaRepository<EstadosUsuarioEntity, Integer> {
    @Query(FIND_ESTADOS_USUARIO_POR_ID_USUARIO)
    List<String> findEstadosUsuarioPorIdUsuario(Integer idUsuario);

}
