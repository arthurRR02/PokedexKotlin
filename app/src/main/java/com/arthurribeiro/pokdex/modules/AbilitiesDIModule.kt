package com.arthurribeiro.pokdex.modules

import com.arthurribeiro.abilities.AbilitiesViewModel
import com.arthurribeiro.abilities.api.AbilitiesRepositoryImp
import com.arthurribeiro.abilities.api.createAbilitiesService
import com.arthurribeiro.abilities.api.getAbilitiesRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val abilitiesDIModule = module {
    single { getAbilitiesRetrofit() }
    factory { createAbilitiesService(get()) }
    factory { AbilitiesRepositoryImp(get(), get()) }
    viewModel { AbilitiesViewModel(get()) }
}