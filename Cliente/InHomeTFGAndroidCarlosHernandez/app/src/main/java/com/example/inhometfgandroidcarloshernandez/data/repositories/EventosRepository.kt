package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.request.DiasEventosRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasEventosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
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
    fun cargarEventos(idCasa: Int, mes: Int, anio: Int): Flow<NetworkResult<DiasEventosResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.cargarEventos(DiasEventosRequestDTO(idCasa, mes, anio))
        emit(result)
    }.flowOn(Dispatchers.IO)
}