package org.tfg.inhometfgcarloshernandez.data.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByCorreo(String correo);

    UsuarioEntity findByNombre(String nombre);

    UsuarioEntity findByTelefono(String telefono);

    @Query(value = "SELECT COUNT(*) FROM usuario u JOIN vive v ON u.id = v.id_usuario WHERE v.id_casa = :idCasa", nativeQuery = true)
    int findNumeroResidentes(int idCasa);
}
