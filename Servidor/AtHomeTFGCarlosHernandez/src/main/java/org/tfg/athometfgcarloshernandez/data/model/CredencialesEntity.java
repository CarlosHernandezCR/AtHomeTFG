package org.tfg.athometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tfg.athometfgcarloshernandez.common.constantes.CredencialesConstantes;

import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = CredencialesConstantes.TABLE_NAME)
public class CredencialesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CredencialesConstantes.COLUMN_ID, nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = CredencialesConstantes.COLUMN_ID_USUARIO, nullable = false)
    private UsuarioEntity idUsuario;

    @Size(max = 255)
    @NotNull
    @Column(name = CredencialesConstantes.COLUMN_PASSWORD, nullable = false)
    private String password;

    @NotNull
    @Basic
    @Column(name = CredencialesConstantes.COLUMN_ACTIVADO, nullable = false)
    private boolean activado;

    @Size(max = 255)
    @Column(name = CredencialesConstantes.COLUMN_CODIGO_ACTIVACION)
    private String codigoActivacion;

    @Column(name = CredencialesConstantes.COLUMN_FECHA_EXPIRACION_CODIGO)
    private Instant fechaExpiracionCodigo;
}