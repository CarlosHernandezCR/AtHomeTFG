package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.domain.usecases.seleccionarcasa.GetCasasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeleccionarCasaViewModel @Inject constructor(
    private val getCasasUseCase: GetCasasUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(SeleccionarCasaContract.SeleccionarCasaState())
    val uiState: StateFlow<SeleccionarCasaContract.SeleccionarCasaState> = _uiState.asStateFlow()

    fun handleEvent(event: SeleccionarCasaContract.SeleccionarCasaEvent){
        when(event){
            is SeleccionarCasaContract.SeleccionarCasaEvent.CargarCasas -> cargarCasas(event.idUsuario);
            SeleccionarCasaContract.SeleccionarCasaEvent.ErrorMostrado -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun cargarCasas(idUsuario:String){
        viewModelScope.launch {
            getCasasUseCase.invoke(idUsuario).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val data = result.data
                        _uiState.update { it.copy(casas = data?.casas ?: emptyList(), isLoading = false) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}