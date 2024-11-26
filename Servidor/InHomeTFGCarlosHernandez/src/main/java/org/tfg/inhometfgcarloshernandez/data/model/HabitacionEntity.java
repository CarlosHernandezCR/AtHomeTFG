package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.HabitacionConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = HabitacionConstantes.TABLE_NAME)
public class HabitacionEntity {
    @Id
    @Column(name = HabitacionConstantes.COLUMN_NOMBRE)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = HabitacionConstantes.COLUMN_ID_CASA, nullable = false)
    private CasaEntity idCasaEntity;
}