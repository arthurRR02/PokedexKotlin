package com.arthurribeiro.pokedex.api

import com.arthurribeiro.foundation.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofit(): Retrofit {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)
    return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()
}

fun createPokedexService(retrofit: Retrofit): PokedexService =
    retrofit.create(PokedexService::class.java)
