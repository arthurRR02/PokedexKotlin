package com.arthurribeiro.foundation.views

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.PathInterpolator
import android.view.animation.Transformation
import android.widget.LinearLayout

interface ViewAnimation {

    val easeOut: Interpolator
        get() = PathInterpolator(.37f, .46f, .63f, 1f)

    fun animationCollapse(view: View, delay: Long = 0, duration: Long? = null) {
        Handler(Looper.getMainLooper()).postDelayed({
            val initialHeight = view.measuredHeight
            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) {
                        view.visibility = View.GONE
                    } else {
                        view.layoutParams.height =
                            initialHeight - (initialHeight * interpolatedTime).toInt()
                        view.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            animation.duration = duration ?: 250
            animation.interpolator = easeOut
            view.startAnimation(animation)
        }, delay)
    }

    fun animationExpand(view: View, delay: Long = 0, duration: Long? = null) {
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val matchParentMeasureSpec = if (view.parent is View) {
                    View.MeasureSpec.makeMeasureSpec(
                        (view.parent as View).width,
                        View.MeasureSpec.EXACTLY
                    )
                } else {
                    View.MeasureSpec.makeMeasureSpec(view.measuredWidth, View.MeasureSpec.EXACTLY)
                }
                val wrapContentMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                view.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
                val targetHeight = view.measuredHeight

                view.layoutParams.height = 1
                view.visibility = View.VISIBLE
                val animation: Animation = object : Animation() {
                    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                        view.layoutParams.height =
                            if (interpolatedTime == 1f) LinearLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                        view.requestLayout()
                    }

                    override fun willChangeBounds(): Boolean {
                        return true
                    }
                }

                animation.duration = duration ?: 250
                animation.interpolator = easeOut
                view.startAnimation(animation)
            } catch (e: Exception) {

            }
        }, delay)
    }

    fun animateArrowToDown(view: View, delay: Long = 0, duration: Long? = null) {
        Handler(Looper.getMainLooper()).postDelayed({
            ValueAnimator.ofFloat(-180f, 0f).apply {
                interpolator = easeOut
                this.duration = duration ?: 250

                addUpdateListener {
                    view.rotation = it.animatedValue as Float
                }

                start()
            }
        }, delay)
    }

    fun animateArrowToUp(view: View, delay: Long = 0, duration: Long? = null) {
        Handler(Looper.getMainLooper()).postDelayed({
            ValueAnimator.ofFloat(0f, -180f).apply {
                interpolator = easeOut
                this.duration = duration ?: 250

                addUpdateListener {
                    view.rotation = it.animatedValue as Float
                }

                start()
            }
        }, delay)
    }
}