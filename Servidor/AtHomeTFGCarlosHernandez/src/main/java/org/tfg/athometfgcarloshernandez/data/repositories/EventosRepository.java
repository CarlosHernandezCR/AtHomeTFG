package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.EventoEntity;

import java.util.List;

import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_DIAS_CON_EVENTOS;
import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_EVENTOS_EN_DIA;

@Repository
public interface EventosRepository extends JpaRepository<EventoEntity, Integer> {

    @Query(FIND_DIAS_CON_EVENTOS)
    List<Integer> findDiasConEventos(int idCasa, int mes, int anio);

    @Query(FIND_EVENTOS_EN_DIA)
    List<EventoEntity> findEventosEnDia(int idCasa, int dia, int mes, int anio);
}

