package com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.HabitacionDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO

interface InmueblesContract {

    data class InmueblesState(
        val idCasa: String = "",
        val habitaciones: List<HabitacionDTO> = emptyList(),
        val muebles: List<MuebleDTO> = emptyList(),
        val cajones: List<CajonDTO> = emptyList(),
        val usuarios: List<UsuarioInmuebles> = emptyList(),
        var idHabitacionActual: String = Constantes.NADA,
        var idMuebleActual: String = Constantes.NADA,
        var mensaje: String? = null,
        var isLoading: Boolean = false,
        var loadingCajones: Boolean = false
    )

    data class UsuarioInmuebles(val id: String, val nombre: String, val color: String)

    sealed class InmueblesEvent {
        data class CargarDatos(val idCasa: String) : InmueblesEvent()
        data class CargarUsuarios(val idCasa: String) : InmueblesEvent()
        data object MensajeMostrado : InmueblesEvent()
        data class CambioHabitacion(val habitacionId: String) : InmueblesEvent()
        data class CambioMueble(val muebleId: String) : InmueblesEvent()
        data class AgregarHabitacion(val habitacion: String) : InmueblesEvent()
        data class AgregarMueble(val mueble: String) : InmueblesEvent()
        data class AgregarCajon(val cajon: String, val idUsuario: String) : InmueblesEvent()
        data class BorrarCajon(val idCajon: String, val idUsuario: String, val idPropietario: String) : InmueblesEvent()
    }
}