package com.example.inhometfgandroidcarloshernandez.data.remote.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.auth0.jwt.JWT
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.data.model.response.AccessTokenResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.UsuarioService
import com.example.inhometfgandroidcarloshernandez.data.remote.di.NetworkModule.dataStore
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
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
        val idUsuario = decodedJWT.getClaim(Constantes.IDUSUARIO).asInt()
        return idUsuario?.toString()
    }

    fun extractIdCasasFromToken(token: String): String? {
        val decodedJWT = JWT.decode(token)
        val idCasas = decodedJWT.getClaim(Constantes.IDCASAS).asList(String::class.java)
        return idCasas?.firstOrNull()
    }

    suspend fun refreshToken(refreshToken: String): NetworkResult<AccessTokenResponseDTO> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val userService = retrofit.create(UsuarioService::class.java)

        val response = userService.refreshToken(refreshToken)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                saveAccessToken(body.accessToken)
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error(ConstantesError.ERROR_REFRESCAR_TOKEN)
            }
        } else {
            return NetworkResult.Error("${response.code()} ${response.message()}")
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