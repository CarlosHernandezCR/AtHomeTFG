package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados

import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO

interface EstadosContract {

    data class EstadosState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val pantallaEstados: PantallaEstadosResponseDTO = PantallaEstadosResponseDTO()
    )

    sealed class EstadosEvent {
        data class LoadCasa(val id: Int) : EstadosEvent()
        data object ErrorMostrado: EstadosEvent()

    }
}