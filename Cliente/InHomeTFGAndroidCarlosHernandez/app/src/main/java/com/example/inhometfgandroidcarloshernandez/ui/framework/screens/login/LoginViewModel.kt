package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.domain.usecases.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login.LoginContract.PortadaState
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login.LoginContract.PortadaEvent
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(PortadaState())
    val uiState: StateFlow<PortadaState> = _uiState.asStateFlow()

    fun handleEvent(event: PortadaEvent) {
        when (event) {
            is PortadaEvent.Login -> login()
            is PortadaEvent.ErrorMostrado -> errorMostrado()
            is PortadaEvent.CorreoChange -> _uiState.update { it.copy(correo = event.correo) }
        }
    }

    private fun login() {
        if(_uiState.value.correo.isEmpty()){
            _uiState.update { it.copy(error = ConstantesError.NO_CORREO) }
            return
        }
        viewModelScope.launch {
            login.invoke(_uiState.value.correo).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val loginResponse = result.data
                        _uiState.update { it.copy(id = loginResponse?.id) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun errorMostrado() {
        _uiState.update { it.copy(error = null, isLoading = false) }
    }
}

