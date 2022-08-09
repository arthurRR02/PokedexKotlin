package com.arthurribeiro.moves.api

import com.arthurribeiro.foundation.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun getMovesRetrofit(): Retrofit {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)

    return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build()).build()
}

fun createMovesService(retrofit: Retrofit): MovesService = retrofit.create(MovesService::class.java)