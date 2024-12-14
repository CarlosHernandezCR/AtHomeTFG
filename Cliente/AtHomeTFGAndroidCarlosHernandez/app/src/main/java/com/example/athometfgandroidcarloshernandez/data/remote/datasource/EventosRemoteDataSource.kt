package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.data.model.request.CrearEventoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.VotarRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.DiasConEventosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.EventosEnDiaResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.EventosService
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
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

    suspend fun votar(votarRequestDTO: VotarRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody { eventosService.votar(votarRequestDTO) }
}