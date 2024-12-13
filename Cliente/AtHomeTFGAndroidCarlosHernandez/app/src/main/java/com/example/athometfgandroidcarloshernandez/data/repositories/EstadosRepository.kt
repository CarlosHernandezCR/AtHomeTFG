package com.example.athometfgandroidcarloshernandez.data.repositories

import com.example.athometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.datasource.EstadosRemoteDataSource
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EstadosRepository @Inject constructor(
    private val remoteDataSource: EstadosRemoteDataSource
){
    fun getDatosCasa(idUsuario:String,idCasa:String): Flow<NetworkResult<PantallaEstadosResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.getDatosCasa(idUsuario,idCasa)
        emit(result)
    }.flowOn(Dispatchers.IO)
}