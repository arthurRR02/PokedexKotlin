package com.arthurribeiro.abilities.api

import com.arthurribeiro.model.model.AbilityDetailDTO
import com.arthurribeiro.network.network.Network

class AbilitiesRepositoryImp(private val service: AbilitiesService, private val network: Network) : AbilitiesRepository{

    override suspend fun getAbilitiesDetail(url: String): AbilityDetailDTO {
        return network.doRequest { service.getAbilitiesDetail(url) }
    }
}