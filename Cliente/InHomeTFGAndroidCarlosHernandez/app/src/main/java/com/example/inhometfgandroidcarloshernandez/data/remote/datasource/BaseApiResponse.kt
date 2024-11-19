package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import retrofit2.Response
import timber.log.Timber

abstract class BaseApiResponse {


    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e)
            return error(ConstantesError.BASE_DE_DATOS_ERROR)
        }
    }

    suspend fun safeApiCallNoBody(apiCall: suspend () -> Response<Unit>): NetworkResult<Boolean> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
               return NetworkResult.Success(true)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e)
            return error(ConstantesError.BASE_DE_DATOS_ERROR)
        }
    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(ConstantesError.BASE_DE_DATOS_ERROR+" $errorMessage")

}