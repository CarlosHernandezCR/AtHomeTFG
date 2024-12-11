package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.CasaService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class EstadosRemoteDataSource @Inject constructor(
    private val casaService: CasaService
) :BaseApiResponse(){
    suspend fun getDatosCasa(idUsuario: String,idCasa:String): NetworkResult<PantallaEstadosResponseDTO> =
        safeApiCall{ casaService.getDatosCasa(idUsuario,idCasa) }
}