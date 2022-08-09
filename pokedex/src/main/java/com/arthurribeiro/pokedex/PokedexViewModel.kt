package com.arthurribeiro.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arthurribeiro.foundation.utils.SingleLiveEvent
import com.arthurribeiro.pokedex.api.PokedexRepositoryImp
import com.arthurribeiro.model.model.PokemonDetailDTO
import com.arthurribeiro.model.model.PokemonListDTO
import com.arthurribeiro.network.network.Resource
import com.arthurribeiro.foundation.utils.launchFromNetwork

class PokedexViewModel(private val serviceImp: PokedexRepositoryImp) : ViewModel() {

    private val _pokemonList = MutableLiveData<Resource<PokemonListDTO>>()
    val pokemonList: LiveData<Resource<PokemonListDTO>> = _pokemonList

    private val _pokemonDetails = SingleLiveEvent<Resource<PokemonDetailDTO>>()
    val pokemonDetails: SingleLiveEvent<Resource<PokemonDetailDTO>> = _pokemonDetails

    fun getPokemons() {
            launchFromNetwork(_pokemonList) {
                serviceImp.getPokemonList()
        }
    }

    fun getPokemonDetail(id: Int) {
        launchFromNetwork(_pokemonDetails) {
            serviceImp.getPokemonDetail(id)
        }
    }

    fun getUrl(id: Int) : String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}