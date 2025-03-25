package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.athometfgcarloshernandez.data.model.MuebleEntity;
import org.tfg.athometfgcarloshernandez.data.model.ProductoEntity;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<ProductoEntity, Integer> {

}