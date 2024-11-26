package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.EstadosConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EstadosConstantes.TABLE_NAME)
public class EstadoEntity {
    @Id
    @Column(name = EstadosConstantes.COLUMN_DESCRIPCION, nullable = false)
    private String descripcion;
}
