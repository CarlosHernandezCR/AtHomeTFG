package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaInmueblesResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InmueblesService {
    @GET(ConstantesPaths.INMUEBLES)
    suspend fun getDatosHabitaciones(@Query(Constantes.idCasa) id: Int): Response<PantallaInmueblesResponseDTO>
}