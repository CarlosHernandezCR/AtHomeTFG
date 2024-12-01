package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.request.CrearEventoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.DiasConEventosRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.EventosEnDiaRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasConEventosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.EventosEnDiaResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EventosService {
    @POST(ConstantesPaths.CREAR_EVENTO)
    suspend fun crearEvento(@Body eventoRequestDTO: CrearEventoRequestDTO): Response<Void>

    @POST(ConstantesPaths.GET_EVENTOS_MES)
    suspend fun cargarEventosDelMes(@Body diasConEventosRequestDTO: DiasConEventosRequestDTO): Response<DiasConEventosResponseDTO>

    @POST(ConstantesPaths.GET_EVENTOS_DIA)
    suspend fun cargarEventosDelDia(@Body eventosEnDiaRequestDTO: EventosEnDiaRequestDTO): Response<EventosEnDiaResponseDTO>
}