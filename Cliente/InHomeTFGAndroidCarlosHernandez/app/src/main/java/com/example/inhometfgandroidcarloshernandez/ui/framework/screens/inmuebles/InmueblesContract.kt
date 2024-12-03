package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO

interface InmueblesContract {

    data class InmueblesState(
        val habitaciones: List<String> = emptyList(),
        val muebles: List<String> = emptyList(),
        val cajones: List<CajonDTO> = emptyList(),
        var habitacionActual:String = Constantes.NADA,
        var muebleActual:String  = Constantes.NADA,
        var error: String = Constantes.NADA,
        var isLoading: Boolean = false,
    )

    sealed class InmueblesEvent{
        data class CargarDatos(val idCasa:Int) : InmueblesEvent()
    }
}