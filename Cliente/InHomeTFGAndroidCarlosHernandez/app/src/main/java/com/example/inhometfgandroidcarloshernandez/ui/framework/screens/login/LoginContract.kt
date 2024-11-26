package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

interface LoginContract {
    sealed class PortadaEvent {
        data class Login(val correo: String) : PortadaEvent()
        data object ErrorMostrado: PortadaEvent()
    }

    data class PortadaState(
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}