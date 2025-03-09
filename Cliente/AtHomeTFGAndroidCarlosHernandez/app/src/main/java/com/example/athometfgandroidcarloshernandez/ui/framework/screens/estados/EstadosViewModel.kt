package com.example.athometfgandroidcarloshernandez.ui.framework.screens.estados


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.athometfgandroidcarloshernandez.domain.usecases.estados.CambiarEstadoUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.estados.GetDatosCasaUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.estados.NuevoEstadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstadosViewModel @Inject constructor(
    private val getDatosCasaUseCase: GetDatosCasaUseCase,
    private val cambiarEstadoUseCase: CambiarEstadoUseCase,
    private val crearEstadoUseCase: NuevoEstadoUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(EstadosContract.EstadosState())
    val uiState: StateFlow<EstadosContract.EstadosState> = _uiState.asStateFlow()

    private val _uiStateEstado = MutableStateFlow(EstadosContract.CambiarEstadoState())
    val uiStateEstado: StateFlow<EstadosContract.CambiarEstadoState> = _uiStateEstado.asStateFlow()


    fun handleEvent(event: EstadosContract.EstadosEvent) {
        when (event) {
            is EstadosContract.EstadosEvent.NuevoEstado -> nuevoEstado(event.estado,event.color,event.idUsuario)
            is EstadosContract.EstadosEvent.CargarCasa -> cargarCasa(event.idUsuario,event.idCasa)
            is EstadosContract.EstadosEvent.ErrorMostrado -> _uiState.value = _uiState.value.copy(mensaje = null)
            is EstadosContract.EstadosEvent.ErrorMostradoEstado -> _uiStateEstado.value = _uiStateEstado.value.copy(mensaje = null)
            is EstadosContract.EstadosEvent.CambiarEstado -> cambiarEstado(event.estado, event.idCasa, event.idUsuario)
            EstadosContract.EstadosEvent.CodigoCopiado -> _uiState.value = _uiState.value.copy(mensaje = Constantes.CODIGO_COPIADO)
        }
    }

    private fun nuevoEstado(estado: String,color:String, idUsuario: String) {
        viewModelScope.launch {
            crearEstadoUseCase.invoke(estado,color,idUsuario).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiStateEstado.update {
                            it.copy(
                                isLoading = false,
                                colorCambiarEstado = uiState.value.pantallaEstados.colorEstado
                            )
                        }
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                pantallaEstados = currentState.pantallaEstados.copy(
                                    estadosDisponibles = currentState.pantallaEstados.estadosDisponibles + estado
                                )
                            )
                        }
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

    private fun cargarCasa(idUsuario:String,idCasa:String) {
        viewModelScope.launch {
            getDatosCasaUseCase.invoke(idUsuario,idCasa).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val datosCasa = result.data ?: PantallaEstadosResponseDTO()
                        _uiState.value = EstadosContract.EstadosState(
                            isLoading = false,
                            pantallaEstados = datosCasa,
                        )
                        _uiStateEstado.value = EstadosContract.CambiarEstadoState(
                            colorCambiarEstado = datosCasa.colorEstado
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
    private fun cambiarEstado(estado: String, idCasa:String, idUsuario: String){
        viewModelScope.launch {
            cambiarEstadoUseCase.invoke(estado,idCasa,idUsuario).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiStateEstado.update {
                            it.copy(
                                colorCambiarEstado = result.data?.color ?: "",
                                isLoading = false
                            )
                        }
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
