package com.example.inhometfgandroidcarloshernandez.data.repositories

import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.inhometfgandroidcarloshernandez.data.model.MuebleDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.AgregarCajonRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.AgregarHabitacionRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.AgregarMuebleRequestDTO
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
    fun getDatosHabitaciones(id:Int,mueble:String?,habitacion:String?): Flow<NetworkResult<PantallaInmueblesResponseDTO>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.getDatosHabitaciones(id,mueble,habitacion)
        emit(result)
    }.flowOn(Dispatchers.IO)
    fun agregarHabitacion(idCasa:Int,habitacion: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarHabitacion(AgregarHabitacionRequestDTO(idCasa,habitacion))
        emit(result)
    }.flowOn(Dispatchers.IO)
    fun agregarMueble(idCasa:Int,nombreHabitacion:String,nombre:String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarMueble(AgregarMuebleRequestDTO(idCasa, nombreHabitacion, nombre))
        emit(result)
    }.flowOn(Dispatchers.IO)
    fun agregarCajon(idCasa:Int,nombreHabitacion:String,nombreMueble:String,nombre:String,idPropietario:Int): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading())
        val result = remoteDataSource.agregarCajon(AgregarCajonRequestDTO(idCasa, nombreHabitacion, nombreMueble, nombre, idPropietario))
        emit(result)
    }.flowOn(Dispatchers.IO)
}