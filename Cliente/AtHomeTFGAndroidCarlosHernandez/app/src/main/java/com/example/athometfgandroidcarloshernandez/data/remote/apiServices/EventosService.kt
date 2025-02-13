package com.example.athometfgandroidcarloshernandez.data.remote.apiServices

import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.athometfgandroidcarloshernandez.data.model.request.CrearEventoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.VotarRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.DiasConEventosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.EventosEnDiaResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EventosService {
    @POST(ConstantesPaths.CREAR_EVENTO)
    suspend fun crearEvento(@Body eventoRequestDTO: CrearEventoRequestDTO): Response<Void>

    @GET(ConstantesPaths.GET_EVENTOS_MES)
    suspend fun cargarEventosDelMes(@Query(ConstantesPaths.IDCASA_QUERY) idCasa: String,
                                    @Query(ConstantesPaths.MES_QUERY) mes: Int,
                                    @Query(ConstantesPaths.ANIO_QUERY) anio: Int): Response<DiasConEventosResponseDTO>

    @GET(ConstantesPaths.GET_EVENTOS_DIA)
    suspend fun cargarEventosDelDia(@Query(ConstantesPaths.IDCASA_QUERY)idCasa: String,
                                    @Query(ConstantesPaths.DIA_QUERY)dia: Int,
                                    @Query(ConstantesPaths.MES_QUERY)mes: Int,
                                    @Query(ConstantesPaths.ANIO_QUERY)anio: Int): Response<EventosEnDiaResponseDTO>

    @POST(ConstantesPaths.VOTAR)
    suspend fun votar(@Body votarRequestDTO: VotarRequestDTO): Response<Void>
}