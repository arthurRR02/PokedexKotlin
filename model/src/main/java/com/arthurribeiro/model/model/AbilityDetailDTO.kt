package com.arthurribeiro.model.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AbilityDetailDTO(
    val name: String? = null,
    val isMainSeries: Boolean? = null,

) : Parcelable