package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val login: LoginUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PortadaState())
    val uiState: StateFlow<PortadaState> = _uiState.asStateFlow()

    fun handleEvent(event: PortadaEvent) {
        when (event) {
            is PortadaEvent.Login -> login(event.correo)
            is PortadaEvent.ErrorMostrado -> errorMostrado()
        }
    }

    private fun login(correo: String) {
        viewModelScope.launch {
            _uiState.value.id.let {
                login.invoke(correo).collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val loginResponse = result.data
                            val id = loginResponse?.id ?: 0
                            _uiState.update { it.copy(id = id, isLoading = false) }
                        }
                        is NetworkResult.Error -> {
                            result.message?.let {
                                showError(it)
                            }
                        }
                        is NetworkResult.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }

            }
        }
    }

    private fun showError(error: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(error = error, isLoading = false) }
        }
    }

    private fun errorMostrado() {
        _uiState.update { it.copy(error = null) }
    }

}