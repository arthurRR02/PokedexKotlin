package com.arthurribeiro.network.network

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

val exceptionHandler =
    CoroutineExceptionHandler { _, ex -> Log.e("Networking", ex.message, ex) }

class Network(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val exHandler: CoroutineExceptionHandler = exceptionHandler,
    private val connectivityHelper: ConnectivityHelper
) {

    suspend fun <T> doRequest(call: suspend () -> Response<T>): T {
        if (!connectivityHelper.hasNetwork()) {
            throw NoConnectionException()
        }
        return withContext(dispatcher + exHandler) {
            val response = call.invoke()

            if (!response.isSuccessful) {
                val jsonObject: JSONObject? = response.errorBody()?.string()?.let { JSONObject(it) }
                throw RuntimeException("Error ${jsonObject?.getJSONObject("error")?.getString("message")}")
            }
            response.body() ?: throw IllegalStateException("body response is null")
        }
    }
}