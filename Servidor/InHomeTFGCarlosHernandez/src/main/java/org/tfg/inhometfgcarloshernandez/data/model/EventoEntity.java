//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import lombok.Generated;

@Entity
@Table(name = "EVENTO")
public class EventoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "tipo", length = 50)
    private @Size(max = 50) String tipo;
    @Column(name = "nombre", nullable = false, length = 100)
    private @Size(max = 100)
    @NotNull String nombre;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "votacion")
    private String votacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador")
    private UsuarioEntity organizador;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_casa")
    private CasaEntity idCasaEntity;

    @Generated
    public Integer getId() {
        return this.id;
    }

    @Generated
    public String getTipo() {
        return this.tipo;
    }

    @Generated
    public String getNombre() {
        return this.nombre;
    }

    @Generated
    public LocalDate getFecha() {
        return this.fecha;
    }

    @Generated
    public String getVotacion() {
        return this.votacion;
    }

    @Generated
    public UsuarioEntity getOrganizador() {
        return this.organizador;
    }

    @Generated
    public CasaEntity getIdCasaEntity() {
        return this.idCasaEntity;
    }

    @Generated
    public void setId(final Integer id) {
        this.id = id;
    }

    @Generated
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    @Generated
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    @Generated
    public void setFecha(final LocalDate fecha) {
        this.fecha = fecha;
    }

    @Generated
    public void setVotacion(final String votacion) {
        this.votacion = votacion;
    }

    @Generated
    public void setOrganizador(final UsuarioEntity organizador) {
        this.organizador = organizador;
    }

    @Generated
    public void setIdCasaEntity(final CasaEntity idCasaEntity) {
        this.idCasaEntity = idCasaEntity;
    }

    @Generated
    public EventoEntity(final Integer id, final String tipo, final String nombre, final LocalDate fecha, final String votacion, final UsuarioEntity organizador, final CasaEntity idCasaEntity) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.fecha = fecha;
        this.votacion = votacion;
        this.organizador = organizador;
        this.idCasaEntity = idCasaEntity;
    }

    @Generated
    public EventoEntity() {
    }
}
