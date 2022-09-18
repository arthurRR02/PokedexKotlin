package com.arthurribeiro.model.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EffectEntryDTO(
    val effect: String? = null
) : Parcelable