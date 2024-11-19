package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.CajonConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = CajonConstantes.TABLE_NAME)
public class CajonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CajonConstantes.COLUMN_ID, nullable = false)
    private Integer id;
    @Column(name = CajonConstantes.COLUMN_NOMBRE, nullable = false, length = 100)
    @NotNull
    private String nombre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CajonConstantes.COLUMN_PROPIETARIO, nullable = false)
    private UsuarioEntity propietario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CajonConstantes.COLUMN_ID_MUEBLE, nullable = false)
    private MuebleEntity muebleEntity;
}
