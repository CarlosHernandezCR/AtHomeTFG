package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import androidx.compose.ui.graphics.ImageBitmap
import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO

interface ProductosContract {
    sealed class ProductosEvent {
        data class CambiarMueble(val idMueble: String) : ProductosEvent()
        data class CambiarCajon(val idCajon: String) : ProductosEvent()
        data class CargarProductos(val idCajon: String) : ProductosEvent()
        data class CambiarCantidad(val idProducto: Int, val aumentar: Boolean) : ProductosEvent()
        data class AgregarProducto(val nombre: String, val cantidad: String, val imagen: String) : ProductosEvent()
        data class DecodeBase64ToImageBitmap(val imagenSinDeco: String) : ProductosEvent()
        data object ErrorMostrado : ProductosEvent()
    }

    data class ProductosState(
        val isLoading: Boolean= false,
        val isLoadingCantidad: Boolean = false,
        val error: String? = null,
        val productos: List<Producto> = emptyList(),
        val muebleActual: String = "",
        val muebles: List<MuebleDTO> = emptyList(),
        val cajonActual: String = "",
        val cajones: List<CajonDTO> = emptyList(),
        val productosCargando: Map<String, Boolean> = emptyMap(),
        val idPropietario: String = ""
    )

    data class Producto(
        val id: Int,
        val nombre: String,
        val unidades: Int,
        val imagen: ImageBitmap?
    )
}