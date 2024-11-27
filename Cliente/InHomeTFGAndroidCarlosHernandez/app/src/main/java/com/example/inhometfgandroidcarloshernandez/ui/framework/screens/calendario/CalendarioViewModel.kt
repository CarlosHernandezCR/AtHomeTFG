package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario.CargarEventosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarioViewModel @Inject constructor(
    private val cargarEventosUseCase: CargarEventosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarioContract.CalendarioState())
    val uiState: StateFlow<CalendarioContract.CalendarioState> = _uiState.asStateFlow()

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
            is CalendarioContract.CalendarioEvent.CargarEventos -> cargarEventos(event.idCasa, _uiState.value.mesActual, _uiState.value.anioActual)
            is CalendarioContract.CalendarioEvent.ErrorMostrado -> _uiState.update { it.copy(error = null, isLoading = false) }
        }
    }

    private fun cargarEventos(idCasa: Int, mes: Int, anio: Int) {
        viewModelScope.launch {
            cargarEventosUseCase.invoke(idCasa, mes + 1, anio).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val diasConEvento = result.data?.diasConEvento ?: emptyList()
                        val updatedDiasEnMes = _uiState.value.diasEnMes.map { semana ->
                            semana.map { dia ->
                                if (diasConEvento.contains(dia.numero) && !dia.otroMes) {
                                    dia.copy(colorFondo = "#FF0000")
                                } else {
                                    dia
                                }
                            }
                        }
                        _uiState.update { currentState ->
                            currentState.copy(diasEnMes = updatedDiasEnMes, isLoading = false)
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
        val COLOR_HOY = "#000000"
        val COLOR_OTRO_MES = "#C4C4C4"

        val dias = mutableListOf<CalendarioContract.DiaCalendario>().apply {
            for (i in 0 until diasPrevios) {
                add(CalendarioContract.DiaCalendario(numero = totalDiasMesAnterior - diasPrevios + i + 1, colorFondo = COLOR_OTRO_MES, otroMes = true))
            }
            for (i in 1..totalDiasEnMes) {
                val colorFondo = when {
                    isToday(anio, mes, i) -> COLOR_HOY
                    else -> COLOR_NORMAL
                }
                add(CalendarioContract.DiaCalendario(numero = i, colorFondo = colorFondo, otroMes = false))
            }
            for (i in 1..(7 - size % 7)) {
                add(CalendarioContract.DiaCalendario(numero = i, colorFondo = COLOR_OTRO_MES, otroMes = true))
            }
        }
        return dias.chunked(7)
    }

    private fun isToday(anio: Int, mes: Int, dia: Int): Boolean {
        val today = Calendar.getInstance()
        return anio == today.get(Calendar.YEAR) && mes == today.get(Calendar.MONTH) && dia == today.get(Calendar.DAY_OF_MONTH)
    }
}