package com.example.athometfgandroidcarloshernandez.data.repositories

import com.example.athometfgandroidcarloshernandez.common.Constantes.IMAGEN
import com.example.athometfgandroidcarloshernandez.common.Constantes.IMAGEN_JPG
import com.example.athometfgandroidcarloshernandez.common.Constantes.IMAGE_JPEG
import com.example.athometfgandroidcarloshernandez.common.Constantes.TEXT_PLAIN
import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarCantidadProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.PedirPrestadoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CargarProductosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.PedirPrestadoResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.datasource.ProductosRemoteDataSource
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Locale
import javax.inject.Inject

class ProductosRepository @Inject constructor(
    private val remoteDataSource: ProductosRemoteDataSource
) {
    fun agregarProducto(nombre: String, cantidad: Int, imagen: ByteArray, idCajon: String): Flow<NetworkResult<ProductoDTO>> =
        flow {
            emit(NetworkResult.Loading())
            val nombreRequest = nombre.toRequestBody(TEXT_PLAIN.toMediaType())
            val cantidadRequest = cantidad.toString().toRequestBody(TEXT_PLAIN.toMediaType())
            val idCajonRequest = idCajon.toRequestBody(TEXT_PLAIN.toMediaType())
            val imagenRequestBody = imagen.toRequestBody(IMAGE_JPEG.toMediaType())
            val imagenPart = MultipartBody.Part.createFormData(IMAGEN.lowercase(Locale.ROOT), IMAGEN_JPG, imagenRequestBody)
            val result = remoteDataSource.agregarProducto(nombreRequest, cantidadRequest, imagenPart, idCajonRequest)
            emit(result)
        }.flowOn(Dispatchers.IO)


    fun cargarProductos(
        idCajon: String?,
        idMueble: String?
    ): Flow<NetworkResult<CargarProductosResponseDTO>> =
        flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.cargarProductos(idCajon, idMueble)
            emit(result)
        }.flowOn(Dispatchers.IO)

    fun cambiarCantidad(idProducto: Int, cantidad: Int): Flow<NetworkResult<Boolean>> =
        flow {
            emit(NetworkResult.Loading())
            val result =
                remoteDataSource.cambiarCantidad(CambiarCantidadProductoRequestDTO(idProducto, cantidad))
            emit(result)
        }.flowOn(Dispatchers.IO)

    fun pedirPrestado(productoId: String, idUsuario: String): Flow<NetworkResult<PedirPrestadoResponseDTO>> =
        flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.pedirPrestado(PedirPrestadoRequestDTO(productoId, idUsuario))
            emit(result)
        }.flowOn(Dispatchers.IO)

}
