package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaInmueblesResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.datasource.InmueblesRemoteDataSource
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InmueblesRepository @Inject constructor(
    private val remoteDataSource: InmueblesRemoteDataSource
) {
    fun getDatosHabitaciones(id:Int): Flow<NetworkResult<PantallaInmueblesResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.getDatosHabitaciones(id)
        emit(result)
    }.flowOn(Dispatchers.IO)
}