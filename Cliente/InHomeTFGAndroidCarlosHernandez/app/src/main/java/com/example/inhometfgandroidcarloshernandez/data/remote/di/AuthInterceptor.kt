package com.example.inhometfgandroidcarloshernandez.data.remote.di

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val isTokenRequest = request.url.encodedPath in listOf(ConstantesPaths.LOGIN_URL, ConstantesPaths.REFRESH_TOKEN_URL)

        if (!isTokenRequest) {
            val accessToken = runBlocking { tokenManager.getAccessToken().firstOrNull() }
            request = request.newBuilder()
                .header(Constantes.AUTHORIZATION, Constantes.BEARER + accessToken)
                .build()
        }
        val response = chain.proceed(request)
        if (response.code == 401) {
            synchronized(this) {
                val currentAccessToken = runBlocking { tokenManager.getAccessToken().firstOrNull() }
                if (request.header(Constantes.AUTHORIZATION) != null && currentAccessToken != null) {
                    val refreshToken = runBlocking { tokenManager.getRefreshToken().firstOrNull() }
                    refreshToken?.let {
                        val newAccessTokenResponse =
                            runBlocking { tokenManager.refreshToken(it) }

                        if (newAccessTokenResponse is NetworkResult.Success) {
                            val newAccessToken = newAccessTokenResponse.data?.accessToken
                            newAccessToken?.let {
                                runBlocking { tokenManager.saveAccessToken(newAccessToken) }
                                request = request.newBuilder()
                                    .header(
                                        Constantes.AUTHORIZATION,
                                        Constantes.BEARER + newAccessToken
                                    )
                                    .build()
                                val newResponse = chain.proceed(request)
                                return newResponse
                            }
                        }
                    }
                }
            }
        }
        return response
    }
}