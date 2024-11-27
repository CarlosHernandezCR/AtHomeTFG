package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario

interface CalendarioContract {

    data class CalendarioState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val mesActual: Int = 0,
        val anioActual: Int = 0,
        val diasEnMes: List<List<DiaCalendario>> = emptyList(),
    )

    data class DiaCalendario(
        val numero: Int,
        val colorFondo: String,
        val otroMes: Boolean,
        val onClick: () -> Unit = {}
    )

    data class EventoCasa(
        val tipo: String,
        val descripcion: String,
        val fechaInicio: String,
        val fechaFin: String,
        val estado: String,
    )

    sealed class CalendarioEvent {
        data class CargarCalendario(val mes: Int, val anio: Int) : CalendarioEvent()
        data class CambiarAnio(val anio: Int) : CalendarioEvent()
        data class CambiarMes(val mes: Int) : CalendarioEvent()
        data class CargarEventos(val idCasa: Int) : CalendarioEvent()
        data object ErrorMostrado:CalendarioEvent()
    }
}

