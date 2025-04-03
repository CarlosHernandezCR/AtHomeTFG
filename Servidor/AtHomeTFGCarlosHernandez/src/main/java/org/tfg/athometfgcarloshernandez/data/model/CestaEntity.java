package org.tfg.athometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.tfg.athometfgcarloshernandez.common.constantes.CestaConstantes;

import static org.tfg.athometfgcarloshernandez.common.constantes.CestaConstantes.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CestaConstantes.TABLE_NAME)
public class CestaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = NOMBRE, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = CANTIDAD, nullable = false)
    private Integer cantidad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = ID_USUARIO, nullable = false)
    private UsuarioEntity idUsuario;

}