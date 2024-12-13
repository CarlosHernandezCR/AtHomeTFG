package com.example.athometfgandroidcarloshernandez.ui.framework.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athometfgandroidcarloshernandez.common.ConstantesError
import com.example.athometfgandroidcarloshernandez.data.remote.di.TokenManager
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.athometfgandroidcarloshernandez.domain.usecases.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

import com.example.athometfgandroidcarloshernandez.ui.framework.screens.login.LoginContract.PortadaState
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.login.LoginContract.PortadaEvent
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val tokenManager: TokenManager,

    ): ViewModel(){
    private val _uiState = MutableStateFlow(PortadaState())
    val uiState: StateFlow<PortadaState> = _uiState.asStateFlow()

    fun handleEvent(event: PortadaEvent) {
        when (event) {
            is PortadaEvent.Login -> login()
            is PortadaEvent.ErrorMostrado -> _uiState.update { it.copy(error = null)}
            is PortadaEvent.IdentificadorChange -> _uiState.update { it.copy(identificador = event.identificador) }
            is PortadaEvent.PasswordChange -> _uiState.update { it.copy(password = event.password) }
        }
    }

    private fun login() {
        if(_uiState.value.identificador.isEmpty() && _uiState.value.password.isEmpty()){
            _uiState.update { it.copy(error = ConstantesError.CAMPO_VACIO_ERROR) }
            return
        }
        viewModelScope.launch {
            login.invoke(_uiState.value.identificador, _uiState.value.password).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val tokens = result.data
                        val idUsuario = tokens?.let { tokenManager.extractIdUsuarioFromToken(tokens.accessToken) }
                        _uiState.update { it.copy(idUsuario = idUsuario, isLoading = false) }
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

