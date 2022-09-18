package com.arthurribeiro.model.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenerationDTO(
    val name: String? = null,
    val url: String? = null
) : Parcelable