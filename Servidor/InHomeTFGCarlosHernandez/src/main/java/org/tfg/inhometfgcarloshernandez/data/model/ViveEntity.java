
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
import org.tfg.inhometfgcarloshernandez.common.constantes.UsuarioConstantes;
import org.tfg.inhometfgcarloshernandez.common.constantes.ViveConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ViveConstantes.TABLE_NAME)
public class ViveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ViveConstantes.COLUMN_ID, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ViveConstantes.COLUMN_ID_USUARIO, nullable = false)
    private UsuarioEntity usuarioEntity;
    @Column(name = ViveConstantes.ESTADO_ACTUAL, nullable = false)
    private String estado;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ViveConstantes.COLUMN_ID_CASA, nullable = false)
    private CasaEntity casaEntity;
}
