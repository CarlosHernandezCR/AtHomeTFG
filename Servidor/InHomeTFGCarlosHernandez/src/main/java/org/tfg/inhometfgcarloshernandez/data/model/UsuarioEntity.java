package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.UsuarioConstantes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = UsuarioConstantes.TABLE_NAME)
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UsuarioConstantes.COLUMN_ID, nullable = false)
    private Integer id;
    @Column(name = UsuarioConstantes.COLUMN_NOMBRE, nullable = false, length = 100)
    private String nombre;
    @Column(name = UsuarioConstantes.COLUMN_CORREO, length = 100)
    private String correo;
    @Column(name = UsuarioConstantes.COLUMN_TELEFONO)
    private String telefono;
    @Column(name = UsuarioConstantes.COLUMN_COLOR, nullable = false)
    private String color;

    public UsuarioEntity(Integer id) {
        this.id = id;
    }
}
