package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
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
    fun login(correo:String): Flow<NetworkResult<LoginResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.login(LoginRequestDTO(correo))
        emit(result)
    }.flowOn(Dispatchers.IO)}