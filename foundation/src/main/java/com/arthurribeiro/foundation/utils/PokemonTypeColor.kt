package com.arthurribeiro.foundation.utils

import com.arthurribeiro.foundation.R

fun getColorByType(type: String): Int {
    return when (type) {
        "normal" -> R.color.normal
        "grass" -> R.color.grass
        "fire" -> R.color.fire
        "water" -> R.color.water
        "electric" -> R.color.electric
        "ice" ->R.color.ice
        "fighting" -> R.color.fighting
        "poison" -> R.color.poison
        "ground" -> R.color.ground
        "flying" -> R.color.flying
        "psychic" -> R.color.psychic
        "bug" -> R.color.bug
        "rock" -> R.color.rock
        "ghost" -> R.color.ghost
        "dragon" -> R.color.dragon
        "dark" -> R.color.dark
        "steel" -> R.color.steel
        "fairy" -> R.color.fairy
        else -> R.color.gray
    }
}