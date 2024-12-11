package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.data.model.request.CambiarEstadoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.RegistroRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.AccessTokenResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.GetUsuariosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.UsuarioService
import com.example.inhometfgandroidcarloshernandez.data.remote.di.TokenManager
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
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
    suspend fun cambiarEstado(cambiarEstadoRequestDTO : CambiarEstadoRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody{ usuarioService.cambiarEstado(cambiarEstadoRequestDTO) }

    suspend fun getUsuarios(idCasa: String): NetworkResult<GetUsuariosResponseDTO> =
        safeApiCall { usuarioService.getUsuarios(idCasa) }

    suspend fun refreshToken(token: String): NetworkResult<AccessTokenResponseDTO> =
        safeApiCall { usuarioService.refreshToken(token) }

    suspend fun registro(registroRequestDTO: RegistroRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody{ usuarioService.registro(registroRequestDTO) }
}