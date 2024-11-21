package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados

interface EstadosContract {

    data class EstadosState(
        val loading: Boolean = false,
        val error: String? = null,
        val nombreCasa: String? = null,
        val direccion: String? = null,
        val usuariosCasa: List<UsuarioCasa> = emptyList(),
        val estadoActual: String? = null
    )

    data class UsuarioCasa (
        val nombre: String = "",
        val estado: String = "",
    )
}