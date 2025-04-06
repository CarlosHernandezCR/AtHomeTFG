package com.example.athometfgandroidcarloshernandez.data.remote.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.auth0.jwt.JWT
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.ConstantesError
import com.example.athometfgandroidcarloshernandez.data.model.response.AccessTokenResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.UsuarioService
import com.example.athometfgandroidcarloshernandez.data.remote.di.NetworkModule.dataStore
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val userService: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey(Constantes.ACCESS_TOKEN)
        private val REFRESH_TOKEN = stringPreferencesKey(Constantes.REFRESH_TOKEN)
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }
    }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token
        }
    }

    fun extractIdUsuarioFromToken(token: String): String? {
        val decodedJWT = JWT.decode(token)
        return decodedJWT.getClaim(Constantes.IDUSUARIO).asInt()?.toString()
    }

    private fun extractIdCasaFromToken(token: String): String? {
        val decodedJWT = JWT.decode(token)
        return decodedJWT.getClaim(Constantes.IDCASA).asInt()?.toString()
    }

    suspend fun obtenerIdUsuario(): String? {
        val token = getAccessToken().firstOrNull()
        return token?.let { extractIdUsuarioFromToken(it) }
    }

    suspend fun obtenerIdCasa(): String? {
        val token = getAccessToken().firstOrNull()
        return token?.let { extractIdCasaFromToken(it) }
    }

    suspend fun refreshToken(refreshToken: String): NetworkResult<AccessTokenResponseDTO> {
        return try {
            val response = userService.refreshToken(refreshToken)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    saveAccessToken(body.accessToken)
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error(ConstantesError.ERROR_REFRESCAR_TOKEN)
                }
            } else {
                NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(ConstantesError.BASE_DE_DATOS_ERROR)
        }
    }


    suspend fun deleteRefreshToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(REFRESH_TOKEN)
        }
    }

    suspend fun deleteAccessToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }
}