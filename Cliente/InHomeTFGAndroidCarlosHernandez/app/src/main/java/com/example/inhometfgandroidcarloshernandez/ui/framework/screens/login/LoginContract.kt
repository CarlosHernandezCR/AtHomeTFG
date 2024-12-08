package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

interface LoginContract {
    sealed class PortadaEvent {
        data object Login : PortadaEvent()
        data object ErrorMostrado: PortadaEvent()
        data class CorreoChange(val correo: String) : PortadaEvent()
    }

    data class PortadaState(
        val id: Int? = null,
        val correo: String = "carlos@ejemplo.com",
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}