package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.request.DiasEventosRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasEventosResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EventosService {
    @POST(ConstantesPaths.GET_EVENTOS_MES)
    suspend fun cargarEventos(@Body diasEventosRequestDTO: DiasEventosRequestDTO): Response<DiasEventosResponseDTO>
}