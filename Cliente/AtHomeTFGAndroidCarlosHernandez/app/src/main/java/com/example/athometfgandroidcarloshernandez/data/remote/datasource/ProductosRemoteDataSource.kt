package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarCantidadProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.PedirPrestadoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CargarProductosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.PedirPrestadoResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.ProductosService
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProductosRemoteDataSource @Inject constructor(
    private val productosService: ProductosService
) :BaseApiResponse() {
    suspend fun cambiarCantidad(cambioProducto: CambiarCantidadProductoRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody { productosService.cambiarCantidad(cambioProducto) }
    suspend fun cargarProductos(idCajon: String?, idMueble: String?): NetworkResult<CargarProductosResponseDTO> =
        safeApiCall { productosService.cargarProductos(idCajon,idMueble) }
    suspend fun agregarProducto(nombre:RequestBody, cantidad: RequestBody, imagen: MultipartBody.Part, idCajon: RequestBody): NetworkResult<ProductoDTO> =
        safeApiCall { productosService.agregarProducto(nombre, cantidad, imagen, idCajon) }
    suspend fun pedirPrestado(pedirPrestadoRequestDTO: PedirPrestadoRequestDTO): NetworkResult<PedirPrestadoResponseDTO> =
        safeApiCall { productosService.pedirPrestado(pedirPrestadoRequestDTO) }
}