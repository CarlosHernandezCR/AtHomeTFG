package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface EstadosService {
    @GET(ConstantesPaths.ESTADOS)
    suspend fun getHomeData(@Body id: Int): Response<PantallaEstadosResponseDTO>
}