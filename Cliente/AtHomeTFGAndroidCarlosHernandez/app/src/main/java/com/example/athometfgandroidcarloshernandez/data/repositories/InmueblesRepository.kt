package com.example.athometfgandroidcarloshernandez.data.repositories

import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarCajonConMuebleRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarCajonRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarHabitacionRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarMuebleRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.PantallaInmueblesResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.datasource.InmueblesRemoteDataSource
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InmueblesRepository @Inject constructor(
    private val remoteDataSource: InmueblesRemoteDataSource
) {
    fun getDatosHabitaciones(id:String,mueble:String?,habitacion:String?): Flow<NetworkResult<PantallaInmueblesResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.getDatosHabitaciones(id,mueble,habitacion)
        emit(result)
    }.flowOn(Dispatchers.IO)
    fun agregarHabitacion(idCasa:String,habitacion: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarHabitacion(AgregarHabitacionRequestDTO(idCasa,habitacion))
        emit(result)
    }.flowOn(Dispatchers.IO)
    fun agregarMueble(idCasa:String,idHabitacion:String,nombre:String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarMueble(AgregarMuebleRequestDTO(idCasa, idHabitacion, nombre))
        emit(result)
    }.flowOn(Dispatchers.IO)
    fun agregarCajon(idCasa:String,nombreHabitacion:String,nombreMueble:String,nombre:String,idPropietario:String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarCajon(AgregarCajonRequestDTO(idCasa, nombreHabitacion, nombreMueble, nombre, idPropietario))
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun borrarCajon(idCajon:String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.borrarCajon(idCajon)
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun agregarCajonConMueble(idMueble:String,nombre:String,idPropietario:String): Flow<NetworkResult<CajonDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarCajonConMueble(AgregarCajonConMuebleRequestDTO(idMueble, nombre, idPropietario))
        emit(result)
    }.flowOn(Dispatchers.IO)
}