package com.arthurribeiro.pokedex.api

import com.arthurribeiro.model.model.PokemonDetailDTO
import com.arthurribeiro.model.model.PokemonListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexService {

    @GET("pokemon/?offset=0&limit=905")
    suspend fun getPokemonList(): Response<PokemonListDTO>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): Response<PokemonDetailDTO>

}