package org.tfg.inhometfgcarloshernandez.data.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByCorreo(String correo);

    UsuarioEntity findByNombre(String nombre);

    UsuarioEntity findByTelefono(String telefono);
}
