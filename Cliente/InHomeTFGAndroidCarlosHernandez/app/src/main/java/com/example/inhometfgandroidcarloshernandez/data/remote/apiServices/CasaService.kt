package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.response.GetCasasResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CasaService {
    @GET(ConstantesPaths.GET_CASAS)
    suspend fun getCasas(@Query(Constantes.IDUSUARIO) idUsuario: String): Response<GetCasasResponseDTO>

    @GET(ConstantesPaths.ESTADOS)
    suspend fun getDatosCasa(@Query(Constantes.IDUSUARIO) idUsuario: String,@Query(Constantes.IDCASA) idCasa: String, @Query(Constantes.TOKEN) token: String): Response<PantallaEstadosResponseDTO>
}
