package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.CasaConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = CasaConstantes.TABLE_NAME)
public class CasaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CasaConstantes.COLUMN_ID, nullable = false)
    private Integer id;
    @Column(name = CasaConstantes.COLUMN_NOMBRE, nullable = false)
    private String nombre;
    @Column(name = CasaConstantes.COLUMN_DIRECCION, nullable = false)
    private String direccion;
    @Column(name = CasaConstantes.COLUMN_CODIGO, nullable = false)
    private String codigo;
    @Column(name = CasaConstantes.COLUMN_CODIGO_INVITACION, nullable = false)
    private String codigoInvitacion;
}