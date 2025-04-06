package com.example.athometfgandroidcarloshernandez.data.repositories

import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarCantidadProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CargarProductosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.datasource.ProductosRemoteDataSource
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductosRepository @Inject constructor(
    private val remoteDataSource: ProductosRemoteDataSource
) {
    fun agregarProducto(
        nombre: String,
        cantidad: Int,
        imagen:String,
        idcajon: String
    ): Flow<NetworkResult<ProductoDTO>> =
        flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.agregarProducto(AgregarProductoRequestDTO(nombre, cantidad, imagen, idcajon))
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
}
