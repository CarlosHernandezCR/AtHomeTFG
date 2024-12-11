package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa

import com.example.inhometfgandroidcarloshernandez.data.model.CasaDetallesDTO


interface SeleccionarCasaContract {
    sealed class SeleccionarCasaEvent{
        data class CargarCasas(val idUsuario: String): SeleccionarCasaEvent()
        data object ErrorMostrado: SeleccionarCasaEvent()
    }

    data class SeleccionarCasaState(
        val casas: List<CasaDetallesDTO> = emptyList(),
        val error: String? = null,
        val isLoading: Boolean = false,
    )
}