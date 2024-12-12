package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.request.CambiarEstadoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.RegistroRequestDTO
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

    fun cambiarEstado(estado: String, idCasa:String, idUsuario: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.cambiarEstado(CambiarEstadoRequestDTO(estado, idCasa, idUsuario))
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

    fun registro(nombre: String, password: String, correo: String, telefono: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.registro(RegistroRequestDTO(nombre, password, correo, telefono))
        emit(result)
    }.flowOn(Dispatchers.IO)
}