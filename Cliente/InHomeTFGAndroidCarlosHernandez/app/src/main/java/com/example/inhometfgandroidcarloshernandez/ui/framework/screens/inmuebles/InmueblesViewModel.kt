package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.domain.usecases.inmuebles.GetDatosHabitacionesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InmueblesViewModel @Inject constructor(
    private val getDatosHabitacionesUseCase: GetDatosHabitacionesUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(InmueblesContract.InmueblesState())
    val uiState: StateFlow<InmueblesContract.InmueblesState> = _uiState.asStateFlow()

    fun handleEvent(event: InmueblesContract.InmueblesEvent) {
        when (event){
            is InmueblesContract.InmueblesEvent.CargarDatos -> cargarDatos(event.idCasa)
        }
    }

    private fun cargarDatos(idCasa:Int){
        viewModelScope.launch {
            getDatosHabitacionesUseCase.invoke(idCasa).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {it.copy(
                            habitaciones = result.data?.habitaciones ?: emptyList(),
                            muebles = result.data?.muebles ?: emptyList(),
                            cajones = result.data?.cajones ?: emptyList(),
                            habitacionActual = result.data?.habitaciones?.get(0) ?: "",
                            muebleActual = result.data?.muebles?.get(0)?: "",
                            isLoading = false
                        ) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message ?: ConstantesError.GET_HABITACIONES_ERROR) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

}