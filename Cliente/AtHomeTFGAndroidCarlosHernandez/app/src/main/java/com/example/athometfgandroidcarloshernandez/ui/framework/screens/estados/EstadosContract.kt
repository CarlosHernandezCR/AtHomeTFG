package com.example.athometfgandroidcarloshernandez.ui.framework.screens.estados

import com.example.athometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO

interface EstadosContract {

    data class EstadosState(
        val isLoading: Boolean = false,
        val mensaje: String? = null,
        val pantallaEstados: PantallaEstadosResponseDTO = PantallaEstadosResponseDTO()
    )

    data class CambiarEstadoState(
        val colorCambiarEstado: String = "",
        val isLoading: Boolean = false,
        val mensaje: String? = null
    )

    sealed class EstadosEvent {
        data class NuevoEstado(val estado: String,val color:String, val idUsuario: String) : EstadosEvent()
        data class CargarCasa(val idUsuario: String,val idCasa:String) : EstadosEvent()
        data object ErrorMostrado: EstadosEvent()
        data object CodigoCopiado: EstadosEvent()
        data class CambiarEstado(val estado: String, val idCasa: String, val idUsuario: String) : EstadosEvent()
        data object ErrorMostradoEstado: EstadosEvent()

    }
}
