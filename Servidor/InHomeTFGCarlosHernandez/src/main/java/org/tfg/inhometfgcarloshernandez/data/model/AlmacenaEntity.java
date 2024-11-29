package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.AlmacenaConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AlmacenaConstantes.TABLE_NAME)
public class AlmacenaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AlmacenaConstantes.COLUMN_ID, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = AlmacenaConstantes.COLUMN_ID_CAJON)
    private CajonEntity idCajon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = AlmacenaConstantes.COLUMN_ID_PRODUCTOS)
    private ProductoEntity idProductos;
    @Column(name = AlmacenaConstantes.COLUMN_CANTIDAD)
    private Integer cantidad;
}