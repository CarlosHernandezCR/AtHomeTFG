package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados

import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO

interface EstadosContract {

    data class EstadosState(
        val isLoading: Boolean = false,
        val mensaje: String? = null,
        val pantallaEstados: PantallaEstadosResponseDTO = PantallaEstadosResponseDTO()
    )

    data class CambiarEstadoState(
        val isLoading: Boolean = false,
        val mensaje: String? = null
    )

    sealed class EstadosEvent {
        data class LoadCasa(val id: Int) : EstadosEvent()
        data object ErrorMostrado: EstadosEvent()
        data class CambiarEstado(val estado: String, val id: Int) : EstadosEvent()
    }
}
