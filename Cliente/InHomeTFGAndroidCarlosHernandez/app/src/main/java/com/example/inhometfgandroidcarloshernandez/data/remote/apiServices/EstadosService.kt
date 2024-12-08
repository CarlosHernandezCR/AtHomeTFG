package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EstadosService {
    @GET(ConstantesPaths.ESTADOS)
    suspend fun getHomeData(@Query(Constantes.ID) id: Int): Response<PantallaEstadosResponseDTO>
}