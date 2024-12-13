package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.registro

interface RegistroContract {
    data class RegistroState(
        val nombre: String = "",
        val password: String = "",
        val correo: String = "",
        val telefono: String = "",
        val color: String = "",
        val isLoading: Boolean = false,
        val mensaje: String? = null,
        val isRegistered: Boolean = false
    )

    sealed class RegistroEvent {
        data class UsernameChange(val username: String) : RegistroEvent()
        data class PasswordChange(val password: String) : RegistroEvent()
        data class CorreoChange(val correo: String) : RegistroEvent()
        data class TelefonoChange(val telefono: String) : RegistroEvent()
        data class ColorChange(val color: String) : RegistroEvent()
        data object Registro : RegistroEvent()
        data object MensajeMostrado : RegistroEvent()
    }
}