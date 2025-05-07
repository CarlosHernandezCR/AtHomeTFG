package com.example.athometfgandroidcarloshernandez.data.remote.util

sealed class NetworkResult<T>(
    var data: T? = null,
    var message: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data) {
        init {
            this.message = message
        }
    }
    class Loading<T> : NetworkResult<T>()
}