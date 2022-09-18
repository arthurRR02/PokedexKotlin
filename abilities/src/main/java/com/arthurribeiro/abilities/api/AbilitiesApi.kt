package com.arthurribeiro.abilities.api

import com.arthurribeiro.foundation.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getAbilitiesRetrofit() : Retrofit{
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)

    return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build()).build()
}

fun createAbilitiesService(retrofit: Retrofit) : AbilitiesService = retrofit.create(AbilitiesService::class.java)