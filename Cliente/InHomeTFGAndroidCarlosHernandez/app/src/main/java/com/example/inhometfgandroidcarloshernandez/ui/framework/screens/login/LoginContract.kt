package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

interface LoginContract {
    sealed class PortadaEvent {
        data object Login : PortadaEvent()
        data object ErrorMostrado: PortadaEvent()
        data class IdentificadorChange(val identificador: String) : PortadaEvent()
        data class PasswordChange(val password: String) : PortadaEvent()
    }

    data class PortadaState(
        val idUsuario: String? = null,
        val identificador: String = "carlos@ejemplo.com",
        val password: String = "1234",
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}