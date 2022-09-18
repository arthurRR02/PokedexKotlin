package com.arthurribeiro.model.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AbilityDetailDTO(
    val name: String? = null,
    val isMainSeries: Boolean? = null,
    @SerializedName("effect_entries") val effectEntries: List<EffectEntryDTO>? = null,
    val generation: GenerationDTO? = null,
    val pokemon: List<AbilityPokemonDTO>? = null
) : Parcelable