package com.arthurribeiro.pokedex.api

import com.arthurribeiro.model.model.PokemonDetailDTO
import com.arthurribeiro.model.model.PokemonListDTO
import com.arthurribeiro.network.network.Network

class PokedexRepositoryImp(private val service: PokedexService, private val network: Network) : PokedexRepository {

    override suspend fun getPokemonList() : PokemonListDTO {
            return network.doRequest { service.getPokemonList() }
    }

    override suspend fun getPokemonDetail(id: Int) : PokemonDetailDTO {
        return network.doRequest { service.getPokemonDetail(id) }
    }
}