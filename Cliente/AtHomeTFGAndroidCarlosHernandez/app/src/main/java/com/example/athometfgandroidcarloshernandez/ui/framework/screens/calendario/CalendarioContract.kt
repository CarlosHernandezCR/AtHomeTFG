package com.example.athometfgandroidcarloshernandez.ui.framework.screens.calendario

interface CalendarioContract {

    data class CalendarioState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val mesActual: Int = 0,
        val anioActual: Int = 0,
        val diasEnMes: List<List<DiaCalendario>> = emptyList(),
        val idCasa: String? = null,
        val mostrarDialogo: Boolean=false,
    )

    data class EventosState(
        val isLoading: Boolean = false,
        val mensaje: String? = null,
        val listaEventos:List<EventoCasa> = emptyList(),
        val diaSeleccionado:Int=-1
    )

    data class DiaCalendario(
        val numero: Int ,
        val colorFondo: String,
    )

    data class EventoCasa(
        val id: String,
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
        data class CargarEventos(val idCasa: String) : CalendarioEvent()
        data object ErrorMostrado:CalendarioEvent()
        data class GetEventosDia(val idCasa: String,val dia: Int): CalendarioEvent()
        data class CambioDiaSeleccionado(val dia: Int): CalendarioEvent()
        data class CrearEvento(val eventoCasa: EventoCasa) : CalendarioEvent()
        data class CambiarAnioPorMes(val avanza: Boolean) : CalendarioEvent()
        data class VotarEvento(val eventoId: String, val idUsuario: String) : CalendarioEvent()

        data object CambiarDialogo : CalendarioEvent()
    }
}

