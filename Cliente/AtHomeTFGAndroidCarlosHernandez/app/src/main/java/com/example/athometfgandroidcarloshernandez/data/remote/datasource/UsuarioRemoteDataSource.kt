package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.common.ConstantesError
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarEstadoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.RegistroRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.AccessTokenResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CambiarEstadoResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.GetUsuariosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.UsuarioService
import com.example.athometfgandroidcarloshernandez.data.remote.di.TokenManager
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import timber.log.Timber
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val usuarioService: UsuarioService,
    private val tokenManager: TokenManager
) :BaseApiResponse() {
    suspend fun login(loginRequestDTO: LoginRequestDTO): NetworkResult<LoginResponseDTO> {
        try {
            val response = usuarioService.login(loginRequestDTO)
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
    suspend fun cambiarEstado(cambiarEstadoRequestDTO : CambiarEstadoRequestDTO): NetworkResult<CambiarEstadoResponseDTO> =
        safeApiCall{ usuarioService.cambiarEstado(cambiarEstadoRequestDTO) }

    suspend fun getUsuarios(idCasa: String): NetworkResult<GetUsuariosResponseDTO> =
        safeApiCall { usuarioService.getUsuarios(idCasa) }

    suspend fun refreshToken(token: String): NetworkResult<AccessTokenResponseDTO> =
        safeApiCall { usuarioService.refreshToken(token) }

    suspend fun registro(registroRequestDTO: RegistroRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody{ usuarioService.registro(registroRequestDTO) }
}