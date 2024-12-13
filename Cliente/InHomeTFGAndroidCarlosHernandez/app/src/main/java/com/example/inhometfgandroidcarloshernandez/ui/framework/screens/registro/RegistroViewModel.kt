package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.domain.usecases.registro.RegistroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val registroUseCase: RegistroUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistroContract.RegistroState())
    val uiState: StateFlow<RegistroContract.RegistroState> = _uiState

    fun handleEvent(event: RegistroContract.RegistroEvent) {
        when (event) {
            is RegistroContract.RegistroEvent.UsernameChange -> {
                _uiState.value = _uiState.value.copy(nombre = event.username)
            }
            is RegistroContract.RegistroEvent.PasswordChange -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }
            is RegistroContract.RegistroEvent.Registro -> {
                registro()
            }
            is RegistroContract.RegistroEvent.MensajeMostrado -> {
                _uiState.value = _uiState.value.copy(mensaje = null)
            }

            is RegistroContract.RegistroEvent.CorreoChange -> {
                _uiState.value = _uiState.value.copy(correo = event.correo)
            }
            is RegistroContract.RegistroEvent.TelefonoChange -> {
                _uiState.value = _uiState.value.copy(telefono = event.telefono)
            }

            is RegistroContract.RegistroEvent.ColorChange -> {
                _uiState.value = _uiState.value.copy(color = event.color)
            }
        }
    }

    private fun registro() {
        if(_uiState.value.nombre.isBlank() || _uiState.value.password.isBlank() || _uiState.value.correo.isBlank()
            || _uiState.value.telefono.isBlank() || _uiState.value.color.isBlank()){
            _uiState.value = _uiState.value.copy(mensaje = ConstantesError.CAMPO_VACIO_ERROR)
            return
        }
        if(!_uiState.value.correo.contains("@")){
            _uiState.value = _uiState.value.copy(mensaje = ConstantesError.CORREO_NO_VALIDO)
            return
        }
        if(_uiState.value.telefono.length != 9){
            _uiState.value = _uiState.value.copy(mensaje = ConstantesError.TELEFONO_NO_VALIDO)
            return
        }
        viewModelScope.launch {
            registroUseCase.invoke(_uiState.value.nombre, _uiState.value.password, _uiState.value.correo, _uiState.value.telefono,_uiState.value.color).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(mensaje = Constantes.CONFIRMAR_REGISTRO, isRegistered = true, isLoading = false) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(mensaje = result.message, isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}