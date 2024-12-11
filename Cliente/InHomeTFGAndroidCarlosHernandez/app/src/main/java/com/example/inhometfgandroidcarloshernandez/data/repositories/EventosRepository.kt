package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.request.CrearEventoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasConEventosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.EventosEnDiaResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.datasource.EventosRemoteDataSource
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EventosRepository @Inject constructor(
    private val remoteDataSource: EventosRemoteDataSource
){
    fun crearEvento(idCasa: String, eventoCasa: CalendarioContract.EventoCasa, fecha:String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.crearEvento(CrearEventoRequestDTO(idCasa, eventoCasa, fecha))
        emit(result)
    }.flowOn(Dispatchers.IO)
    fun cargarEventosDelMes(idCasa: String, mes: Int, anio: Int): Flow<NetworkResult<DiasConEventosResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.cargarEventosDelMes(idCasa, mes, anio)
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun cargarEventosDelDia(idCasa: String,dia:Int, mes: Int, anio: Int): Flow<NetworkResult<EventosEnDiaResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.cargarEventosDelDia(idCasa,dia, mes, anio)
        emit(result)
    }.flowOn(Dispatchers.IO)
}