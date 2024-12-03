package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaInmueblesResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.InmueblesService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class InmueblesRemoteDataSource @Inject constructor(
    private val inmueblesService: InmueblesService
) :BaseApiResponse() {
    suspend fun getDatosHabitaciones(id:Int): NetworkResult<PantallaInmueblesResponseDTO> =
        safeApiCall{ inmueblesService.getDatosHabitaciones(id) }
}