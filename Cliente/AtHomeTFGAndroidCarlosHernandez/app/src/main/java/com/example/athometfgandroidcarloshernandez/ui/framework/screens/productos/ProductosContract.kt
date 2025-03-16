package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

interface ProductosContract {
    sealed class ProductosEvent {
        data class CargarProductos(val idCajon:String, val idPropietario: String, val idUsuario: String): ProductosEvent()
        data object ErrorMostrado: ProductosEvent()
    }

    data class ProductosState(
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}