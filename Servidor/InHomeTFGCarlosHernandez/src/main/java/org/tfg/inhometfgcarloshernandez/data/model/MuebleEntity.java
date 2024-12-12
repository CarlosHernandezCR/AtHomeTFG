package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.MuebleConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = MuebleConstantes.TABLE_NAME)
public class MuebleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MuebleConstantes.COLUMN_ID, nullable = false)
    private Integer id;

    @Column(name = MuebleConstantes.COLUMN_NOMBRE, nullable = false, length = 100, unique = true)
    private @Size(max = 100)
    @NotNull String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = MuebleConstantes.ID_HABITACION, nullable = false)
    private HabitacionEntity idHabitacionEntity;

}