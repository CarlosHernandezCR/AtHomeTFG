package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.datasource.EstadosRemoteDataSource
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EstadosRepository @Inject constructor(
    private val remoteDataSource: EstadosRemoteDataSource
){
    fun getHomeData(id:Int): Flow<NetworkResult<PantallaEstadosResponseDTO>> {
        val flowOn = flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getHomeData(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
        return flowOn
    }
}