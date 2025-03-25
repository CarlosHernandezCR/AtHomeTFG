package org.tfg.athometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.common.constantes.ProductoConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ProductoConstantes.TABLE_NAME)
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ProductoConstantes.COLUMN_ID, nullable = false)
    private Integer id;

    @Column(name = ProductoConstantes.COLUMN_NOMBRE, nullable = false, length = 100)
    private @Size(max = 100) @NotNull String nombre;

    @Column(name = ProductoConstantes.COLUMN_FOTO)
    private @Size(max = 255) String foto;
}