package com.example.athometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athometfgandroidcarloshernandez.common.ConstantesError
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.athometfgandroidcarloshernandez.domain.usecases.seleccionarcasa.AgregarCasaUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.seleccionarcasa.GetCasasUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.seleccionarcasa.UnirseCasaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeleccionarCasaViewModel @Inject constructor(
    private val getCasasUseCase: GetCasasUseCase,
    private val agregarCasaUseCase: AgregarCasaUseCase,
    private val unirseCasaUseCase: UnirseCasaUseCase,
): ViewModel(){
    private val _uiState = MutableStateFlow(SeleccionarCasaContract.SeleccionarCasaState())
    val uiState: StateFlow<SeleccionarCasaContract.SeleccionarCasaState> = _uiState.asStateFlow()

    fun handleEvent(event: SeleccionarCasaContract.SeleccionarCasaEvent){
        when(event){
            is SeleccionarCasaContract.SeleccionarCasaEvent.CargarCasas -> cargarCasas(event.idUsuario)
            is SeleccionarCasaContract.SeleccionarCasaEvent.ErrorMostrado -> _uiState.update { it.copy(error = null) }
            is SeleccionarCasaContract.SeleccionarCasaEvent.AgregarCasa -> agregarCasa(event.idUsuario, event.nombre, event.direccion, event.codigoPostal)
            is SeleccionarCasaContract.SeleccionarCasaEvent.UnirseCasa -> unirseCasa(event.idUsuario, event.codigoInvitacion)
        }
    }

    private fun unirseCasa(idUsuario: String, codigoInvitacion: String) {
        viewModelScope.launch {
            unirseCasaUseCase.invoke(idUsuario, codigoInvitacion).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        cargarCasas(idUsuario)
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = ConstantesError.ERROR_CODIGO, isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun agregarCasa(
        idUsuario: String,
        nombre: String,
        direccion: String,
        codigoPostal: String
    ) {
        if(nombre.isEmpty() || direccion.isEmpty() || codigoPostal.isEmpty()){
            _uiState.update { it.copy(error = ConstantesError.CAMPO_VACIO_ERROR) }
            return
        }
        viewModelScope.launch {
            agregarCasaUseCase.invoke(idUsuario, nombre, direccion, codigoPostal).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        cargarCasas(idUsuario)
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