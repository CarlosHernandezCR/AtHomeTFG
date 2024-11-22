package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.domain.usecases.estados.GetHomeDataUseCase
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstadosViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadosContract.EstadosState())
    val uiState: StateFlow<EstadosContract.EstadosState> = _uiState.asStateFlow()

    fun handleEvent(event: EstadosContract.EstadosEvent) {
        when (event) {
            is EstadosContract.EstadosEvent.LoadCasa -> getHomeData(event.id)
            is EstadosContract.EstadosEvent.ErrorMostrado -> _uiState.value = _uiState.value.copy(error = null)
        }
    }

    private fun getHomeData(id:Int) {
        viewModelScope.launch {
            getHomeDataUseCase.invoke(id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.value = EstadosContract.EstadosState(pantallaEstados = result.data ?: PantallaEstadosResponseDTO())
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = EstadosContract.EstadosState(error = result.message)
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = EstadosContract.EstadosState(isLoading = true)
                    }
                }
            }
        }
    }
}
