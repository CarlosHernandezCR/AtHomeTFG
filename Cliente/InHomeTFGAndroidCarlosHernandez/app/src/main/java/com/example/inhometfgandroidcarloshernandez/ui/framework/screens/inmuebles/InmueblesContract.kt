package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.inhometfgandroidcarloshernandez.data.model.MuebleDTO

interface InmueblesContract {

    data class InmueblesState(
        val idCasa: Int = 0,
        val habitaciones: List<String> = emptyList(),
        val muebles: List<MuebleDTO> = emptyList(),
        val cajones: List<CajonDTO> = emptyList(),
        val usuarios: List<Usuario> = emptyList(),
        var habitacionActual:String = Constantes.NADA,
        var muebleActual:String  = Constantes.NADA,
        var mensaje: String? = null,
        var isLoading: Boolean = false,
    )

    data class Usuario(val id: String, val nombre: String)

    sealed class InmueblesEvent{
        data class CargarDatos(val idCasa:Int) : InmueblesEvent()
        data class CargarUsuarios(val idCasa:Int) : InmueblesEvent()
        data object MensajeMostrado: InmueblesEvent()
        data class CambioHabitacion(val habitacion:String) : InmueblesEvent()
        data class CambioMueble(val mueble:String) : InmueblesEvent()
        data class CajonSeleccionado(val cajon:String) : InmueblesEvent()
        data class AgregarHabitacion(val habitacion: String) : InmueblesEvent()
        data class AgregarMueble(val mueble: String) : InmueblesEvent()
        data class AgregarCajon(val cajon: String, val idUsuario:Int) : InmueblesEvent()
    }
}