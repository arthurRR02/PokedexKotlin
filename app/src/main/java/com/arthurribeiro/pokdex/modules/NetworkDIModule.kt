package com.arthurribeiro.pokdex.modules

import com.arthurribeiro.network.network.ConnectivityHelper
import com.arthurribeiro.network.network.Network
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkDIModule = module {
    factory { ConnectivityHelper(androidContext()) }
    single { Network(connectivityHelper = get()) }
}