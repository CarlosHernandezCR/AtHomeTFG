package org.tfg.athometfgcarloshernandez.data.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tfg.athometfgcarloshernandez.common.constantes.EventoConstantes;

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

    @Column(name = EventoConstantes.COLUMN_TIPO)
    private String tipo;

    @Column(name = EventoConstantes.COLUMN_NOMBRE, nullable = false)
    private String nombre;

    @Column(name = EventoConstantes.COLUMN_FECHA)
    private LocalDate fecha;

    @Column(name = EventoConstantes.COLUMN_VOTACION)
    private String votacion;

    @Column(name = EventoConstantes.COLUMN_HORA_COMIENZO)
    private LocalTime horaComienzo;

    @Column(name = EventoConstantes.COLUMN_HORA_FIN)
    private LocalTime horaFin;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = EventoConstantes.COLUMN_ORGANIZADOR)
    private UsuarioEntity organizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EventoConstantes.COLUMN_ID_CASA)
    private CasaEntity idCasaEntity;
}