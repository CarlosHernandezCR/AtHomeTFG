package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.request.CambiarEstadoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.AccessTokenResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.GetUsuariosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.datasource.UsuarioRemoteDataSource
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val remoteDataSource: UsuarioRemoteDataSource
){
    fun login(identificador: String, password: String): Flow<NetworkResult<LoginResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.login(LoginRequestDTO(identificador,password))
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun cambiarEstado(estado: String, id:String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.cambiarEstado(CambiarEstadoRequestDTO(estado, id))
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun getUsuarios(idCasa: String): Flow<NetworkResult<GetUsuariosResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.getUsuarios(idCasa)
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun refreshToken(token: String): Flow<NetworkResult<AccessTokenResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.refreshToken(token)
        emit(result)
    }.flowOn(Dispatchers.IO)
}