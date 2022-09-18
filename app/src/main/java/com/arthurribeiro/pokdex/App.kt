package com.arthurribeiro.pokdex

import android.app.Application
import com.arthurribeiro.pokdex.modules.abilitiesDIModule
import com.arthurribeiro.pokdex.modules.movesDIModule
import com.arthurribeiro.pokdex.modules.networkDIModule
import com.arthurribeiro.pokdex.modules.pokedexDIModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkDIModule,
                pokedexDIModule,
                movesDIModule,
                abilitiesDIModule
            )
        }
    }
}