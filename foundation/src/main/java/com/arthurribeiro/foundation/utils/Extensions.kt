package com.arthurribeiro.foundation.utils

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurribeiro.network.network.Resource
import com.arthurribeiro.network.network.exceptionHandler
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun View.getImageFromUrl(activity: Activity, url: String, onResReadyCallback: (Drawable) -> Unit) {
    Glide.with(activity.baseContext)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val drawable: Drawable = BitmapDrawable(resources, resource)
                onResReadyCallback.invoke(drawable)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

fun <T> ViewModel.launchFromNetwork(liveData: MutableLiveData<Resource<T>>, call: suspend () -> T) {
    viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
        liveData.value = Resource.Loading
        kotlin.runCatching {
            call.invoke()
        }.onSuccess {
            liveData.value = Resource.success(it)
        }.onFailure {
            liveData.value = Resource.error(it)
            { this@launchFromNetwork.launchFromNetwork(liveData, call) }
        }
    }
}

fun TextView.setPokemonId(id: Int) {
    this.text = when (id.toString().length) {
        1 -> "#00$id"
        2 -> "#0$id"
        else -> "#$id"
    }
}

fun Int.decimetresToCentimeters() : String{
    val centimeters = this * 10
    return "${centimeters}cm"
}

fun Int.hectogramsToKilograms() : String{
    val kilograms = this.div(10f)
    return "${kilograms}kg"
}

fun View.setColorAnimation(colorFrom: Int, colorTo: Int, context: Context){
    val animator: ObjectAnimator? = ObjectAnimator.ofInt(
            this,
            "backgroundColor",
            ContextCompat.getColor(context, colorFrom),
            ContextCompat.getColor(context, colorTo)
        )

    animator?.setEvaluator(ArgbEvaluator())
    animator?.start()
}

fun Int.toPercent() : String = "${this}%"

fun String.firstCharUpperCase() = this.replaceFirstChar { it.uppercase() }
