package com.arthurribeiro.pokdex.modules

import com.arthurribeiro.pokedex.PokedexViewModel
import com.arthurribeiro.pokedex.api.PokedexRepositoryImp
import com.arthurribeiro.pokedex.api.createPokedexService
import com.arthurribeiro.pokedex.api.getRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexDIModule = module {
    single { getRetrofit() }
    factory { createPokedexService(get()) }
    factory { PokedexRepositoryImp(get(), get()) }
    viewModel { PokedexViewModel(get()) }
}