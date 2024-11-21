package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.EstadosService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class EstadosRemoteDataSource @Inject constructor(
    private val estadosService: EstadosService
) :BaseApiResponse(){
    suspend fun getHomeData(id: Int): NetworkResult<PantallaEstadosResponseDTO> =
        safeApiCall{ estadosService.getHomeData(id) }
}