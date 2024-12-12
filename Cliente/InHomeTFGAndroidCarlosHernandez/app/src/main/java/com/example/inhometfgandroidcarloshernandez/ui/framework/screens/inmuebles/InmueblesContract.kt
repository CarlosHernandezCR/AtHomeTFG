package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.inhometfgandroidcarloshernandez.data.model.HabitacionDTO
import com.example.inhometfgandroidcarloshernandez.data.model.MuebleDTO

interface InmueblesContract {

    data class InmueblesState(
        val idCasa: String = "",
        val habitaciones: List<HabitacionDTO> = emptyList(),
        val muebles: List<MuebleDTO> = emptyList(),
        val cajones: List<CajonDTO> = emptyList(),
        val usuarios: List<Usuario> = emptyList(),
        var idHabitacionActual: String = Constantes.NADA,
        var muebleActual: String = Constantes.NADA,
        var mensaje: String? = null,
        var isLoading: Boolean = false,
    )

    data class Usuario(val id: String, val nombre: String)

    sealed class InmueblesEvent {
        data class CargarDatos(val idCasa: String) : InmueblesEvent()
        data class CargarUsuarios(val idCasa: String) : InmueblesEvent()
        data object MensajeMostrado : InmueblesEvent()
        data class CambioHabitacion(val habitacionId: String) : InmueblesEvent()
        data class CambioMueble(val muebleId: String) : InmueblesEvent()
        data class CajonSeleccionado(val cajon: String) : InmueblesEvent()
        data class AgregarHabitacion(val habitacion: String) : InmueblesEvent()
        data class AgregarMueble(val mueble: String) : InmueblesEvent()
        data class AgregarCajon(val cajon: String, val idUsuario: String) : InmueblesEvent()
    }
}