package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.request.CrearEventoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasConEventosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.EventosEnDiaResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.EventosService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import retrofit2.http.Query
import javax.inject.Inject

class EventosRemoteDataSource @Inject constructor(
    private val eventosService: EventosService
) :BaseApiResponse(){
    suspend fun crearEvento(crearEventoRequestDTO: CrearEventoRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody{ eventosService.crearEvento(crearEventoRequestDTO) }

    suspend fun cargarEventosDelMes(idCasa:String, mes:Int, anio:Int): NetworkResult<DiasConEventosResponseDTO> =
        safeApiCall{ eventosService.cargarEventosDelMes(idCasa,mes,anio) }

    suspend fun cargarEventosDelDia(idCasa: String, dia: Int, mes: Int, anio: Int): NetworkResult<EventosEnDiaResponseDTO> =
        safeApiCall{ eventosService.cargarEventosDelDia(idCasa,dia,mes,anio) }
}