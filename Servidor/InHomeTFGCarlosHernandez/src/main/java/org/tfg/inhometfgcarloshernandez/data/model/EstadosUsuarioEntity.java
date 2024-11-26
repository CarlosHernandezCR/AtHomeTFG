package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.EstadosUsuarioConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EstadosUsuarioConstantes.TABLE_NAME)
public class EstadosUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EstadosUsuarioConstantes.COLUMN_ID, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EstadosUsuarioConstantes.COLUMN_ID_USUARIO, nullable = false)
    private UsuarioEntity usuarioEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EstadosUsuarioConstantes.COLUMN_DESCRIPCION_ESTADO, nullable = false)
    private EstadoEntity estadoEntity;
}