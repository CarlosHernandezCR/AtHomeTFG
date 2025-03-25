package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CargarProductosRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CargarProductosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.ProductosService
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class ProductosRemoteDataSource @Inject constructor(
    private val productosService: ProductosService
) :BaseApiResponse() {
    suspend fun cambiarCantidad(cambioProducto: CambiarProductoRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody { productosService.cambiarCantidad(cambioProducto) }
    suspend fun cargarProductos(cargarProductos: CargarProductosRequestDTO): NetworkResult<CargarProductosResponseDTO> =
        safeApiCall { productosService.cargarProductos(cargarProductos) }
}