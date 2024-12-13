package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.CredencialesEntity;
import org.tfg.athometfgcarloshernandez.data.model.UsuarioEntity;

@Repository
public interface CredencialesRepository extends JpaRepository<CredencialesEntity, Integer> {
    CredencialesEntity findByIdUsuario(UsuarioEntity idUsuario);

    CredencialesEntity findByCodigoActivacion(String codigoActivacion);
}
