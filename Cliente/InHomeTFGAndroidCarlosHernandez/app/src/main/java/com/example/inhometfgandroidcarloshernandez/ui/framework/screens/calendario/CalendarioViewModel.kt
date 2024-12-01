package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario.CargarEventosDelDiaUseCase
import com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario.CargarEventosDelMesUseCase
import com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario.CrearEventoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarioViewModel @Inject constructor(
    private val cargarEventosDelMesUseCase: CargarEventosDelMesUseCase,
    private val cargarEventosDelDiaUseCase: CargarEventosDelDiaUseCase,
    private val crearEventoUseCase: CrearEventoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarioContract.CalendarioState())
    val uiState: StateFlow<CalendarioContract.CalendarioState> = _uiState.asStateFlow()
    private val _uiStateEventos = MutableStateFlow(CalendarioContract.EventosState())
    val uiStateEventos: StateFlow<CalendarioContract.EventosState> = _uiStateEventos.asStateFlow()

    init {
        val calendar = Calendar.getInstance()
        updateFullState(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
    }

    fun handleEvent(event: CalendarioContract.CalendarioEvent) {
        when (event) {
            is CalendarioContract.CalendarioEvent.CargarCalendario -> updateFullState(event.mes, event.anio)
            is CalendarioContract.CalendarioEvent.CambiarAnio -> updateFullState(_uiState.value.mesActual, event.anio)
            is CalendarioContract.CalendarioEvent.CambiarMes -> {
                val (newMonth, newYear) = ajustarFechas(event.mes, _uiState.value.anioActual)
                updateFullState(newMonth, newYear)
            }
            is CalendarioContract.CalendarioEvent.CargarEventos -> cargarEventosDelMes(event.idCasa, _uiState.value.mesActual, _uiState.value.anioActual)
            is CalendarioContract.CalendarioEvent.ErrorMostrado -> _uiState.update { it.copy(error = null, isLoading = false) }
            is CalendarioContract.CalendarioEvent.GetEventosDia -> getEventosDia(event.idCasa, event.dia,_uiState.value.mesActual, _uiState.value.anioActual)
            is CalendarioContract.CalendarioEvent.CambioDiaSeleccionado -> cambiarDiaSeleccionado(event.dia)
            is CalendarioContract.CalendarioEvent.CrearEvento -> crearEvento(event.eventoCasa)
            is CalendarioContract.CalendarioEvent.CambiarDialogo -> _uiState.update { it.copy(mostrarDialogo = !_uiState.value.mostrarDialogo) }
        }
    }
    private fun cambiarDiaSeleccionado(dia:Int){
        _uiStateEventos.update { it.copy(diaSeleccionado = dia) }
        getEventosDia(_uiState.value.idCasa,_uiStateEventos.value.diaSeleccionado,_uiState.value.mesActual, _uiState.value.anioActual)
    }

    private fun crearEvento(evento: CalendarioContract.EventoCasa) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        try {
            val horaInicio = LocalTime.parse(evento.horaComienzo, formatter)
            val horaFin = LocalTime.parse(evento.horaFin, formatter)

            if (horaInicio.isAfter(horaFin) || horaInicio == horaFin) {
                _uiStateEventos.value = _uiStateEventos.value.copy(
                    mensaje = "La hora de inicio debe ser anterior a la hora de fin."
                )
                return
            }
        } catch (e: DateTimeParseException) {
            _uiStateEventos.value = _uiStateEventos.value.copy(
                mensaje = "Formato de hora inválido. Use el formato HH:mm."
            )
            return
        }
        val fechaCompuesta:String
        if(_uiStateEventos.value.diaSeleccionado<10){
            fechaCompuesta = "0${_uiStateEventos.value.diaSeleccionado}-${_uiState.value.mesActual + 1}-${_uiState.value.anioActual}"
        }else{
            fechaCompuesta = "${_uiStateEventos.value.diaSeleccionado}-${_uiState.value.mesActual + 1}-${_uiState.value.anioActual}"
        }
        viewModelScope.launch {
            crearEventoUseCase.invoke(
                _uiState.value.idCasa,
                evento,
                fechaCompuesta
            ).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiStateEventos.update { currentState ->
                            currentState.copy(mensaje = "Evento creado correctamente", isLoading = false)
                        }
                        cargarEventosDelMes(_uiState.value.idCasa, _uiState.value.mesActual, _uiState.value.anioActual)
                        getEventosDia(_uiState.value.idCasa,_uiStateEventos.value.diaSeleccionado,_uiState.value.mesActual, _uiState.value.anioActual)
                    }
                    is NetworkResult.Error -> {
                        _uiStateEventos.update { it.copy(mensaje = result.message ?: "Error", isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiStateEventos.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun getEventosDia(idCasa: Int,dia: Int, mes: Int, anio: Int) {
        viewModelScope.launch {
            cargarEventosDelDiaUseCase.invoke(idCasa,dia, mes + 1, anio).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val eventos = result.data?.eventosResponseDTO ?: emptyList()
                        val eventosCasa = eventos.map { evento ->
                            CalendarioContract.EventoCasa(
                                tipo = evento.tipo,
                                nombre = evento.nombre,
                                organizador = evento.organizador,
                                horaComienzo = evento.horaComienzo.toString(),
                                horaFin = evento.horaFin.toString(),
                                votacion = evento.votacion
                            )
                        }
                        _uiStateEventos.update { currentState ->
                            currentState.copy(listaEventos = eventosCasa, isLoading = false)
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiStateEventos.update { it.copy(mensaje = result.message ?: "Error", isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiStateEventos.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun cargarEventosDelMes(idCasa: Int, mes: Int, anio: Int) {
        viewModelScope.launch {
            cargarEventosDelMesUseCase.invoke(idCasa, mes + 1, anio).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val diasConEvento = result.data?.diasConEvento ?: emptyList()
                        val updatedDiasEnMes = _uiState.value.diasEnMes.map { semana ->
                            semana.map { dia ->
                                if (diasConEvento.contains(dia.numero) && dia.colorFondo != "#C4C4C4") {
                                    dia.copy(colorFondo = "#FF0000")
                                } else {
                                    dia
                                }
                            }
                        }
                        _uiState.update { currentState ->
                            currentState.copy(idCasa=idCasa,diasEnMes = updatedDiasEnMes, isLoading = false)
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message ?: "Error", isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
    private fun updateFullState(mes: Int, anio: Int) {
        _uiState.update {
            it.copy(
                mesActual = mes,
                anioActual = anio,
                diasEnMes = obtenerDiasDelMes(mes, anio)
            )
        }
        cargarEventosDelMes(_uiState.value.idCasa, mes, anio)
    }

    private fun ajustarFechas(mes: Int, anio: Int): Pair<Int, Int> {
        var newMonth = mes
        var newYear = anio
        if (newMonth == 12) {
            newYear += 1
            newMonth = 0
        } else if (newMonth == -1) {
            newYear -= 1
            newMonth = 11
        }
        return Pair(newMonth, newYear)
    }

    private fun obtenerDiasDelMes(mes: Int, anio: Int): List<List<CalendarioContract.DiaCalendario>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, anio)
            set(Calendar.MONTH, mes)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val primerDiaSemana = calendar.get(Calendar.DAY_OF_WEEK)
        val totalDiasEnMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val diasPrevios = if (primerDiaSemana == Calendar.SUNDAY) 6 else primerDiaSemana - 2

        calendar.add(Calendar.MONTH, -1)
        val totalDiasMesAnterior = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar.add(Calendar.MONTH, 1)

        //provisional
        val COLOR_NORMAL = "#FFFFFF"
        val COLOR_HOY = "#0000ff"
        val COLOR_OTRO_MES = "#C4C4C4"

        val dias = mutableListOf<CalendarioContract.DiaCalendario>().apply {
            for (i in 0 until diasPrevios) {
                add(CalendarioContract.DiaCalendario(numero = totalDiasMesAnterior - diasPrevios + i + 1, colorFondo = COLOR_OTRO_MES))
            }
            for (i in 1..totalDiasEnMes) {
                val colorFondo = when {
                    esHoy(anio, mes, i) -> COLOR_HOY
                    else -> COLOR_NORMAL
                }
                add(CalendarioContract.DiaCalendario(numero = i, colorFondo = colorFondo))
            }
            for (i in 1..(7 - size % 7)) {
                add(CalendarioContract.DiaCalendario(numero = i, colorFondo = COLOR_OTRO_MES))
            }
        }
        return dias.chunked(7)
    }

    private fun esHoy(anio: Int, mes: Int, dia: Int): Boolean {
        val today = Calendar.getInstance()
        return anio == today.get(Calendar.YEAR) && mes == today.get(Calendar.MONTH) && dia == today.get(Calendar.DAY_OF_MONTH)
    }
}