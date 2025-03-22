package com.example.athometfgandroidcarloshernandez.data.repositories

import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
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
    fun cambiarCantidad(idProducto: String, cantidad: Int): Flow<NetworkResult<LoginResponseDTO>> =
        flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.cambiarCantidad(CambiarProductoRequestDTO(idProducto, cantidad))
            emit(result)
        }.flowOn(Dispatchers.IO)
}
