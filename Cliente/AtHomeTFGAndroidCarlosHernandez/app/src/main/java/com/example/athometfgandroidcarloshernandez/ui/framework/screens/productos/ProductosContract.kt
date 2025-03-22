package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO
import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO

interface ProductosContract {
    sealed class ProductosEvent {
        data class CambiarMueble(val idMueble: String) : ProductosEvent()
        data class CambiarCajon(val idCajon: String) : ProductosEvent()
        data class CargarProductos(val idCajon: String, val idPropietario: String, val idUsuario: String) : ProductosEvent()
        data class CambiarCantidad(val idProducto: String, val aumentar: Boolean) : ProductosEvent()
        data object ErrorMostrado : ProductosEvent()
    }

    data class ProductosState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val productos: List<ProductoDTO> = emptyList(),
        val muebleActual: String = "",
        val muebles: List<MuebleDTO> = emptyList(),
        val cajonActual: String = "",
        val cajones: List<CajonDTO> = emptyList()
    )
}