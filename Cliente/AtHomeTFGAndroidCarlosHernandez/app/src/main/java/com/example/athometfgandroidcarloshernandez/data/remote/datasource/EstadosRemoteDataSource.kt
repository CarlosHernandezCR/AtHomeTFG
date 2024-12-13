package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.common.ConstantesError
import com.example.athometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.CasaService
import com.example.athometfgandroidcarloshernandez.data.remote.di.TokenManager
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject

class EstadosRemoteDataSource @Inject constructor(
    private val casaService: CasaService,
    private val tokenManager: TokenManager
) :BaseApiResponse() {
    suspend fun getDatosCasa( idUsuario: String, idCasa: String): NetworkResult<PantallaEstadosResponseDTO> {
        val token = tokenManager.getAccessToken().firstOrNull()
            ?: return NetworkResult.Error(ConstantesError.ERROR_INICIO_SESION)
        try {
            val response = casaService.getDatosCasa(idUsuario, idCasa,token)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    tokenManager.saveAccessToken(body.accessToken)
                    tokenManager.saveRefreshToken(body.refreshToken)
                    return NetworkResult.Success(body)
                }
                else {
                    return NetworkResult.Error(ConstantesError.ERROR_INICIO_SESION)
                }
            } else {
                return NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Timber.e(e)
            return NetworkResult.Error(ConstantesError.BASE_DE_DATOS_ERROR)
        }
    }
}