package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.athometfgcarloshernandez.data.model.CestaEntity;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;

import java.util.List;
import java.util.Optional;

import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_CASAS_POR_USUARIO_ID;

@Repository
public interface CestaRepository extends JpaRepository<CestaEntity, Integer> {

    CestaEntity getByIdUsuarioAndNombre(UsuarioEntity propietario, String nombre);
}
