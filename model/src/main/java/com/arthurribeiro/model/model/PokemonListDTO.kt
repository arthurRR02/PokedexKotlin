package com.arthurribeiro.model.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonListDTO(
    val results: List<PokemonDTO>? = null
) : Parcelable

