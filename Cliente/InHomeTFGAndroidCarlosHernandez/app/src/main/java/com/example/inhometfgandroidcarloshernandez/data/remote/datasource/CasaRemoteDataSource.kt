package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.data.model.response.GetCasasResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.CasaService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class CasaRemoteDataSource @Inject constructor(
    private val casaService: CasaService
) : BaseApiResponse() {
    suspend fun getCasas(idUsuario: String): NetworkResult<GetCasasResponseDTO> =
        safeApiCall {casaService.getCasas(idUsuario)}
}