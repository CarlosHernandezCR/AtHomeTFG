package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarCasaRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.UnirseCasaRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.GetCasasResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.CasaService
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class CasaRemoteDataSource @Inject constructor(
    private val casaService: CasaService
) : BaseApiResponse() {
    suspend fun getCasas(idUsuario: String): NetworkResult<GetCasasResponseDTO> =
        safeApiCall {casaService.getCasas(idUsuario)}
    suspend fun agregarCasa(agregarCasaRequestDTO: AgregarCasaRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody {casaService.agregarCasa(agregarCasaRequestDTO)}
    suspend fun unirseCasa(unirseCasaRequestDTO: UnirseCasaRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody {casaService.unirseCasa(unirseCasaRequestDTO)}
}