package com.arthurribeiro.abilities.api

import com.arthurribeiro.model.model.AbilityDetailDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface AbilitiesService {

    @GET
    suspend fun getAbilitiesDetail(@Url url: String) : Response<AbilityDetailDTO>
}