package com.example.athometfgandroidcarloshernandez.data.remote.di

import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.athometfgandroidcarloshernandez.domain.usecases.login.RefreshTokenUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenUseCaseProvider: Provider<RefreshTokenUseCase>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = runBlocking { tokenManager.getAccessToken().firstOrNull() }
        val refreshToken = runBlocking { tokenManager.getRefreshToken().firstOrNull() }

        if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
            return null
        }

        val result = runBlocking {
            val refreshTokenUseCase = refreshTokenUseCaseProvider.get()
            when (val refreshRequest = refreshToken.let { refreshTokenUseCase.invoke(it) }) {
                is NetworkResult.Success<*> -> {
                    refreshRequest.data?.let {
                        val newAccessToken = it.toString()
                        runBlocking { tokenManager.saveAccessToken(newAccessToken) }
                        response.request.newBuilder()
                            .header(Constantes.AUTHORIZATION, "${Constantes.BEARER} $newAccessToken")
                            .build()
                    }
                }
                else -> null
            }
        }
        return result
    }
}