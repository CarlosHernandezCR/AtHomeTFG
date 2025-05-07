package org.tfg.athometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.common.constantes.PedidoConstantes;

@Entity
@Table(name = PedidoConstantes.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PedidoConstantes.COLUMN_ID, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PedidoConstantes.ID_PRODUCTO, nullable = false)
    private AlmacenaEntity producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PedidoConstantes.ID_PROPIETARIO, nullable = false)
    private UsuarioEntity propietario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PedidoConstantes.ID_USUARIO, nullable = false)
    private UsuarioEntity usuarioSolicitante;

    @Column(name = PedidoConstantes.COLUMN_ACEPTADO)
    private Boolean aceptado;
}
