package com.arthurribeiro.model.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetailDTO(
    val abilities: List<AbilityDTO>,
    val height: Int? = null,
    val id: Int? = null,
    @SerializedName("location_area_encounters") val locationAreaEncounters: String? = null,
    val moves: List<MoveDTO>? = null,
    val stats: List<StatsDTO>? = null,
    val name: String? = null,
    val types: List<PokemonTypeDTO>? = null,
    val weight: Int? = null
) : Parcelable

@Parcelize
data class PokemonTypeDTO(
    val type: TypeDTO? = null
) : Parcelable

@Parcelize
data class AbilityDTO(
    val ability: AbilityInformationDTO
) : Parcelable

@Parcelize
data class AbilityInformationDTO(
    val name: String?,
    val url: String?,
) : Parcelable

@Parcelize
data class MoveDTO(
    val move: MoveInformationDTO? = null
) : Parcelable

@Parcelize
data class MoveInformationDTO(
    val name: String? = null,
    val url: String? = null
) : Parcelable

@Parcelize
data class StatsDTO(
    @SerializedName("base_stat") val baseStat: Int? = null,
    val stat: StatsInformationDTO? = null
) : Parcelable

@Parcelize
data class StatsInformationDTO(
    val name: String? = null,
    val url: String? = null
) : Parcelable