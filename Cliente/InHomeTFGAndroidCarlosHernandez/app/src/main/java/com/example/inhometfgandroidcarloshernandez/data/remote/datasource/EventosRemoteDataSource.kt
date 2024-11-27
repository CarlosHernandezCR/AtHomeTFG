package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.data.model.request.DiasEventosRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasEventosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.EventosService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class EventosRemoteDataSource @Inject constructor(
    private val eventosService: EventosService
) :BaseApiResponse(){
    suspend fun cargarEventos(diasEventosRequestDTO: DiasEventosRequestDTO): NetworkResult<DiasEventosResponseDTO> =
        safeApiCall{ eventosService.cargarEventos(diasEventosRequestDTO) }
}