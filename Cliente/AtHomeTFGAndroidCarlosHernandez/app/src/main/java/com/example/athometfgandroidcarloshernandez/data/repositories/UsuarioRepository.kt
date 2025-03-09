package com.example.athometfgandroidcarloshernandez.data.repositories

import com.example.athometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.RegistroRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.AccessTokenResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.GetUsuariosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.datasource.UsuarioRemoteDataSource
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
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

    fun registro(nombre: String, password: String, correo: String, telefono: String, color: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.registro(RegistroRequestDTO(nombre, password, correo, telefono,color))
        emit(result)
    }.flowOn(Dispatchers.IO)
}