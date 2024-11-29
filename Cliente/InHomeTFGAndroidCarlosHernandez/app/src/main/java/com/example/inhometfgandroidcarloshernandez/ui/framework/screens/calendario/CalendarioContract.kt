package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario

interface CalendarioContract {

    data class CalendarioState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val mesActual: Int = 0,
        val anioActual: Int = 0,
        val diasEnMes: List<List<DiaCalendario>> = emptyList(),
        val idCasa: Int=0,
    )

    data class EventosState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val listaEventos:List<EventoCasa> = emptyList()
    )

    data class DiaCalendario(
        val numero: Int,
        val colorFondo: String,
    )

    data class EventoCasa(
        val tipo: String,
        val nombre: String,
        val organizador:String,
        val horaComienzo: String,
        val horaFin: String,
        val votacion: String,
    )

    sealed class CalendarioEvent {
        data class CargarCalendario(val mes: Int, val anio: Int) : CalendarioEvent()
        data class CambiarAnio(val anio: Int) : CalendarioEvent()
        data class CambiarMes(val mes: Int) : CalendarioEvent()
        data class CargarEventos(val idCasa: Int) : CalendarioEvent()
        data object ErrorMostrado:CalendarioEvent()
        data class GetEventosDia(val idCasa: Int,val dia: Int): CalendarioEvent()
    }
}

