package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.request.DiasConEventosRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.EventosEnDiaRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasConEventosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.EventosEnDiaResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.datasource.EventosRemoteDataSource
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EventosRepository @Inject constructor(
    private val remoteDataSource: EventosRemoteDataSource
){
    fun cargarEventosDelMes(idCasa: Int, mes: Int, anio: Int): Flow<NetworkResult<DiasConEventosResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.cargarEventosDelMes(DiasConEventosRequestDTO(idCasa, mes, anio))
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun cargarEventosDelDia(idCasa: Int,dia:Int, mes: Int, anio: Int): Flow<NetworkResult<EventosEnDiaResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.cargarEventosDelDia(EventosEnDiaRequestDTO(idCasa,dia, mes, anio))
        emit(result)
    }.flowOn(Dispatchers.IO)
}