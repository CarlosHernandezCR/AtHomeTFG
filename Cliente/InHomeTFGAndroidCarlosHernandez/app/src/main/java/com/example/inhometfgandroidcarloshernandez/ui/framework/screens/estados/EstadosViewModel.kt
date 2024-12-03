package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.domain.usecases.estados.GetDatosCasaUseCase
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.domain.usecases.estados.ChangeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstadosViewModel @Inject constructor(
    private val getDatosCasaUseCase: GetDatosCasaUseCase,
    private val changeStateUseCase: ChangeStateUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(EstadosContract.EstadosState())
    val uiState: StateFlow<EstadosContract.EstadosState> = _uiState.asStateFlow()

    private val _uiStateEstado = MutableStateFlow(EstadosContract.CambiarEstadoState())
    val uiStateEstado: StateFlow<EstadosContract.CambiarEstadoState> = _uiStateEstado.asStateFlow()


    fun handleEvent(event: EstadosContract.EstadosEvent) {
        when (event) {
            is EstadosContract.EstadosEvent.LoadCasa -> getHomeData(event.id)
            is EstadosContract.EstadosEvent.ErrorMostrado -> _uiState.value = _uiState.value.copy(mensaje = null)
            is EstadosContract.EstadosEvent.ErrorMostradoEstado -> _uiStateEstado.value = _uiStateEstado.value.copy(mensaje = null)
            is EstadosContract.EstadosEvent.CambiarEstado -> cambiarEstado(event.estado, event.id)
        }
    }

    private fun getHomeData(id:Int) {
        viewModelScope.launch {
            getDatosCasaUseCase.invoke(id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val datosCasa = result.data ?: PantallaEstadosResponseDTO()
                        _uiState.value = EstadosContract.EstadosState(
                            isLoading = false,
                            pantallaEstados = datosCasa
                        )
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = EstadosContract.EstadosState(mensaje = result.message)
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = EstadosContract.EstadosState(isLoading = true)
                    }
                }
            }
        }
    }
    private fun cambiarEstado(estado:String, id:Int){
        viewModelScope.launch {
            changeStateUseCase.invoke(estado,id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiStateEstado.value = EstadosContract.CambiarEstadoState(
                            isLoading = false,
                            mensaje = Constantes.ESTADO_CAMBIADO
                        )
                    }
                    is NetworkResult.Error -> {
                        _uiStateEstado.value = EstadosContract.CambiarEstadoState(mensaje = result.message)
                    }
                    is NetworkResult.Loading -> {
                        _uiStateEstado.value = EstadosContract.CambiarEstadoState(isLoading = true)
                    }
                }
            }
        }
    }
}
