package org.tfg.athometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.common.constantes.VotoConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = VotoConstantes.TABLE_NAME)
public class VotoEntity {
    @Id
    @Column(name = VotoConstantes.COLUMN_ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = VotoConstantes.COLUMN_ID_USUARIO, nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = VotoConstantes.COLUMN_ID_EVENTO, nullable = false)
    private EventoEntity evento;
}
