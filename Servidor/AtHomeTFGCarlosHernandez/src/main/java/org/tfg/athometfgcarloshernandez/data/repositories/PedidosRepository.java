package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.data.model.AlmacenaEntity;
import org.tfg.athometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.athometfgcarloshernandez.data.model.PedidoEntity;

import java.util.List;
import java.util.Optional;

import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_BY_ID_WITH_CAJON_AND_PROPIETARIO;

@Repository
public interface PedidosRepository extends JpaRepository<PedidoEntity, Integer> {

}