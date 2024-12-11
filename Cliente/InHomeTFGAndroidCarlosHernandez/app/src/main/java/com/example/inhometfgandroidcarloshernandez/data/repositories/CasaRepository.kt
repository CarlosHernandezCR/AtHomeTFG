package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.response.GetCasasResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.datasource.CasaRemoteDataSource
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CasaRepository @Inject constructor(
    private val remoteDataSource: CasaRemoteDataSource,
){
    fun getCasas(idUsuario:String): Flow<NetworkResult<GetCasasResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.getCasas(idUsuario)
        emit(result)
    }.flowOn(Dispatchers.IO)
}