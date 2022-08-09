package com.arthurribeiro.foundation.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.arthurribeiro.foundation.R
import com.arthurribeiro.foundation.databinding.ViewTextWithIconBinding

class TextWithIcon @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewTextWithIconBinding.inflate(LayoutInflater.from(context), this, true)

    var viewText: String? = null
    set(value) {
        field = value
        binding.text.text = value
    }

    var viewIconDrawable: Drawable? = null
    set(value) {
        field = value
        binding.icon.setImageDrawable(value)
    }

    var viewTextIconColor: ColorStateList? = null
    set(value) {
        field = value
        value?.let { binding.icon.drawable.setTintList(it) }
    }

    var viewTextColor: Int? = ContextCompat.getColor(context, R.color.white)
    set(value) {
        field = value
        value?.let{ binding.text.setTextColor(it) }
    }

    var viewTextSize: Float? = resources.getDimension(R.dimen.body_text)
    set(value) {
        field = value
        value?.let { binding.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, it)  }
    }

    init {
        setLayout(attrs)
    }

    private fun setLayout(attr: AttributeSet?){
        attr?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.TextWithIcon)

            viewText = attributes.getString(R.styleable.TextWithIcon_text)
            viewIconDrawable = attributes.getDrawable(R.styleable.TextWithIcon_iconDrawable)
            viewTextColor = attributes.getColor(R.styleable.TextWithIcon_textColor, ContextCompat.getColor(context, R.color.white))
            viewTextSize = attributes.getDimension(R.styleable.TextWithIcon_textSize, resources.getDimension(R.dimen.body_text))
            viewTextIconColor = attributes.getColorStateList(R.styleable.TextWithIcon_textIconColor)

            if (viewIconDrawable == null) viewIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_info)
            if (viewTextIconColor == null) viewTextIconColor = ContextCompat.getColorStateList(context, R.color.white)

            attributes.recycle()
        }
    }

    fun setOnIconClick(click: (view: View) -> Unit){
        binding.icon.setOnClickListener { view -> click.invoke(view) }
    }
}