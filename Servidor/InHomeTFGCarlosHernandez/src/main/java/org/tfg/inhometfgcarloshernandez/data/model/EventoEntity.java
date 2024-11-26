package org.tfg.inhometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.inhometfgcarloshernandez.common.constantes.EventoConstantes;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = EventoConstantes.TABLE_NAME)
public class EventoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EventoConstantes.COLUMN_ID, nullable = false)
    private Integer id;

    @Column(name = EventoConstantes.COLUMN_TIPO, length = 50)
    private @Size(max = 50) String tipo;

    @Column(name = EventoConstantes.COLUMN_NOMBRE, nullable = false, length = 100)
    private @Size(max = 100) @NotNull String nombre;

    @Column(name = EventoConstantes.COLUMN_FECHA)
    private LocalDate fecha;

    @Column(name = EventoConstantes.COLUMN_VOTACION)
    private String votacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EventoConstantes.COLUMN_ORGANIZADOR)
    private UsuarioEntity organizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EventoConstantes.COLUMN_ID_CASA)
    private CasaEntity idCasaEntity;
}