package com.arthurribeiro.model.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AbilityPokemonDTO(
    @SerializedName("is_hidden") val isHidden: Boolean? = null,
    val pokemon: PokemonDTO? = null,
    val slot: Int? = null
) : Parcelable