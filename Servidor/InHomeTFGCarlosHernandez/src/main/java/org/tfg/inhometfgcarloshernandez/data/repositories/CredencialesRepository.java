package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.CredencialesEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;

@Repository
public interface CredencialesRepository extends JpaRepository<CredencialesEntity, Integer> {
    CredencialesEntity findByIdUsuario(UsuarioEntity idUsuario);
}
