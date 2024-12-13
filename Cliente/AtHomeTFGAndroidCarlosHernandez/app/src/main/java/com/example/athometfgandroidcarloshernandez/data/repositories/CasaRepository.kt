package com.example.athometfgandroidcarloshernandez.data.repositories

import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarCasaRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.UnirseCasaRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.GetCasasResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.datasource.CasaRemoteDataSource
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
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

    fun agregarCasa(idUsuario: String, nombre: String, direccion: String, codigoPostal: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarCasa(AgregarCasaRequestDTO(idUsuario, nombre, direccion, codigoPostal))
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun unirseCasa(idUsuario: String, codigoInvitacion: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.unirseCasa(UnirseCasaRequestDTO(idUsuario, codigoInvitacion))
        emit(result)
    }.flowOn(Dispatchers.IO)
}