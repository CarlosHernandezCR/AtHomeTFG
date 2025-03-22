package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.PantallaInmueblesResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.ProductosService
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class ProductosRemoteDataSource @Inject constructor(
    private val productosService: ProductosService
) :BaseApiResponse() {
    suspend fun getDatosProductos(id: String,mueble: String?,habitacion: String?): NetworkResult<PantallaInmueblesResponseDTO> =
        safeApiCall { inmueblesService.getDatosHabitaciones(id, mueble, habitacion) }

    suspend fun cambiarCantidad(cambioProducto: CambiarProductoRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody { productosService.cambiarCantidad(cambioProducto) }
}