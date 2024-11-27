package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.EventoEntity;

import java.util.List;

@Repository
public interface EventosRepository extends JpaRepository<EventoEntity, Long> {

    @Query("SELECT DISTINCT DAY(e.fecha) FROM EventoEntity e WHERE e.idCasaEntity.id = :idCasa AND MONTH(e.fecha) = :mes AND YEAR(e.fecha) = :anio")
    List<Integer> findDiasConEventos(@Param("idCasa") int idCasa, @Param("mes") int mes, @Param("anio") int anio);
}

