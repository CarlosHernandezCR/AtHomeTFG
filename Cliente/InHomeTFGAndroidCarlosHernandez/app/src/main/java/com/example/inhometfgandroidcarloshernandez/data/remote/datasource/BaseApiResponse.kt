package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

abstract class BaseApiResponse {


    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            val errorBody = response.errorBody()?.string()
            return error("${response.code()} ${errorBody ?: response.message()}")
        } catch (e: HttpException) {
            Timber.e(e)
            return error("${e.code()} ${e.message()}")
        } catch (e: Exception) {
            Timber.e(e)
            return error(ConstantesError.BASE_DE_DATOS_ERROR)
        }
    }
    suspend fun safeApiCallNoBody(apiCall: suspend () -> Response<Void>): NetworkResult<Boolean> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                NetworkResult.Success(true)
            } else {
                error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Timber.e(e)
            error(ConstantesError.BASE_DE_DATOS_ERROR)
        }
    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(errorMessage)
}