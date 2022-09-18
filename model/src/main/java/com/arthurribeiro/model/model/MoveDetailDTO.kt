package com.arthurribeiro.model.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoveDetailDTO(
    val accuracy: Int? = null,
    @SerializedName("damage_class") val damageClass: DamageClassDTO? = null,
    @SerializedName("effect_chance") val effectChance: Int? = null,
    @SerializedName("effect_entries") val effectEntries: List<EffectEntryDTO>? = null,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntriesDTO>? = null,
    val generation: GenerationDTO? = null,
    val id: Int? = null,
    @SerializedName("learned_by_pokemon") val learnedByPokemon: List<PokemonDTO>? = null,
    val meta: MetaDTO? = null,
    val name: String? = null,
    val power: Int? = null,
    val pp: Int? = null,
    val target: TargetDTO? = null,
    val type: TypeDTO? = null,
) : Parcelable

@Parcelize
data class DamageClassDTO(
    val name: String? = null,
    val url: String? = null
) : Parcelable

@Parcelize
data class FlavorTextEntriesDTO(
    @SerializedName("flavor_text") val flavorText: String? = null,
    @SerializedName("version_group") val versionGroup: VersionGroupDTO? = null
) : Parcelable

@Parcelize
data class VersionGroupDTO(
    val name: String? = null,
    val url: String? = null
) : Parcelable

@Parcelize
data class TargetDTO(
    val name: String? = null,
    val url: String? = null
) : Parcelable

@Parcelize
data class MetaDTO(
    val ailment: AilmentDTO? = null,
    @SerializedName("crit_rate") val criticalRate: Int? = null,
    val drain: Int? = null,
    @SerializedName("flinch_chance") val flinchChance: Int? = null,
    val healing: Int? = null,
    @SerializedName("max_hits") val maxHits: Int? = null,
    @SerializedName("max_turns") val maxTurns: Int? = null,
    @SerializedName("min_hits") val minHits: Int? = null,
    @SerializedName("min_turns") val minTurns: Int? = null,
    @SerializedName("stat_chance") val statChance: Int? = null
) : Parcelable

@Parcelize
data class AilmentDTO(
    val name: String? = null,
    val url: String? = null
) : Parcelable