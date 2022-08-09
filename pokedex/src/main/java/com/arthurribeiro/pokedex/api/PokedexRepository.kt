package com.arthurribeiro.pokedex.api

import com.arthurribeiro.model.model.PokemonDetailDTO
import com.arthurribeiro.model.model.PokemonListDTO

interface PokedexRepository {

    suspend fun getPokemonList() : PokemonListDTO

    suspend fun getPokemonDetail(id: Int) : PokemonDetailDTO
}