package com.arthurribeiro.network.network

import android.util.Log

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()

    class Success<out T>(val data: T) : Resource<T>()

    sealed class Error : Resource<Nothing>() {
        class NoConnection(retry: (() -> Any?)?) : Error()
        class Unknown(retry: (() -> Any?)?) : Error()
    }

    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)
         fun <T> error(exception: Throwable,  retry: (() -> Any?)? = null): Resource<T> {
            Log.e("Networking", exception.message, exception)
            return when (exception) {
                is NoConnectionException -> {
                    Error.NoConnection(retry)
                }
                else -> {
                    Error.Unknown(retry)
                }
            }
        }

    }
}
