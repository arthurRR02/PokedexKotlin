package com.arthurribeiro.abilities.api

import com.arthurribeiro.model.model.AbilityDetailDTO

interface AbilitiesRepository {

    suspend fun getAbilitiesDetail(url: String) : AbilityDetailDTO
}