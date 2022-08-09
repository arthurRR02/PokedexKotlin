package com.arthurribeiro.pokdex.modules

import com.arthurribeiro.moves.MovesViewModel
import com.arthurribeiro.moves.api.MoveRepositoryImp
import com.arthurribeiro.moves.api.createMovesService
import com.arthurribeiro.moves.api.getMovesRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movesDIModule = module {
    single { getMovesRetrofit() }
    factory { createMovesService(get()) }
    factory { MoveRepositoryImp(get(), get()) }
    viewModel { MovesViewModel(get()) }
}