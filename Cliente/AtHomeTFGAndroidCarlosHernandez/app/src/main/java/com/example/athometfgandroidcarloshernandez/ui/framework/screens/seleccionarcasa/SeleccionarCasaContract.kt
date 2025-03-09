package com.example.athometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa

import com.example.athometfgandroidcarloshernandez.data.model.CasaDetallesDTO


interface SeleccionarCasaContract {
    sealed class SeleccionarCasaEvent{
        data class SalirCasa(val idUsuario: String,val idCasa: String): SeleccionarCasaEvent()
        data class CargarCasas(val idUsuario: String): SeleccionarCasaEvent()
        data class AgregarCasa(val idUsuario: String,val nombre:String,val direccion:String,val codigoPostal:String): SeleccionarCasaEvent()
        data class UnirseCasa(val idUsuario: String, val codigoInvitacion: String): SeleccionarCasaEvent()
        data object ErrorMostrado: SeleccionarCasaEvent()
    }

    data class SeleccionarCasaState(
        val casas: List<CasaDetallesDTO> = emptyList(),
        val error: String? = null,
        val isLoading: Boolean = false,
    )
}