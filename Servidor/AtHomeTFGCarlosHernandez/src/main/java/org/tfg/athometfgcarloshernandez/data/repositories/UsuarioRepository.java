package org.tfg.athometfgcarloshernandez.data.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;

import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_NUMERO_RESIDENTES;
import static org.tfg.athometfgcarloshernandez.common.constantes.QueryConstantes.FIND_USUARIOS_BY_CASA;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByCorreo(String correo);

    Optional<UsuarioEntity> findByNombre(String nombre);

    Optional<UsuarioEntity> findByTelefono(String telefono);

    @Query(value = FIND_NUMERO_RESIDENTES, nativeQuery = true)
    int findNumeroResidentes(int idCasa);

    @Query(value = FIND_USUARIOS_BY_CASA, nativeQuery = true)
    List<UsuarioEntity> findByIdCasaEntityId(int idCasa);

}
