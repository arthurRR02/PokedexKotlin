package com.arthurribeiro.foundation.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.arthurribeiro.foundation.R
import com.arthurribeiro.foundation.databinding.ViewSimpleHeaderBinding

class SimpleHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSimpleHeaderBinding.inflate(LayoutInflater.from(context), this, true)

    var isLeftIconVisible: Boolean = false
    set(value) {
        field = value
        binding.leftIconButton.isVisible = value
    }

    var isRightIconVisible: Boolean = false
    set(value) {
        field = value
        binding.rightIconButton.isVisible = value
    }

    var leftIconDrawable: Drawable? = null
    set(value) {
        field = value
        value?.let {
            binding.leftIconButton.setImageDrawable(value)
        }
    }

    var rightIconDrawable: Drawable? = null
    set(value) {
        field = value
        value?.let {
            binding.rightIconButton.setImageDrawable(value)
        }
    }

    var iconColor: Int? = null
    set(value) {
        field = value
        value?.let {
            leftIconDrawable?.setTintList(ContextCompat.getColorStateList(context, R.color.white))
            rightIconDrawable?.setTintList(ContextCompat.getColorStateList(context, R.color.white))
        }
    }

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?){
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.SimpleHeader)

            setBackgroundColor(Color.TRANSPARENT)

            isLeftIconVisible = attributes.getBoolean(R.styleable.SimpleHeader_isLeftIconVisible, false)
            isRightIconVisible = attributes.getBoolean(R.styleable.SimpleHeader_isRightIconVisible, false)
            leftIconDrawable = attributes.getDrawable(R.styleable.SimpleHeader_leftIconDrawable)
            rightIconDrawable = attributes.getDrawable(R.styleable.SimpleHeader_rightIconDrawable)
            iconColor = attributes.getColor(R.styleable.SimpleHeader_iconColor, ContextCompat.getColor(context, R.color.white))
            attributes.recycle()
        }
    }

    fun setOnLeftIconClickListener(click: (view: View) -> Unit){
        binding.leftIconButton.setOnClickListener { view -> click.invoke(view) }
    }

    fun setOnRightIconClickListener(click: (view: View) -> Unit){
        binding.rightIconButton.setOnClickListener { view -> click.invoke(view) }
    }
}