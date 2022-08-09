package com.arthurribeiro.foundation.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.arthurribeiro.foundation.R
import com.arthurribeiro.foundation.databinding.ViewAccordionBinding

class Accordion @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr), ViewAnimation {

    private val binding = ViewAccordionBinding.inflate(LayoutInflater.from(context), this, true)

    var accordionBackground: ColorStateList? = null
    set(value) {
        field = value
        binding.root.background.setTintList(value)
    }

    var titleText: String? = null
    set(value) {
        field = value
        binding.accordionTitle.text = value
    }

    var iconColor: ColorStateList? = null
    set(value) {
        field = value
        binding.accordionIcon.background.setTintList(value)
    }

    var dividerColor: Int? = null
    set(value) {
        field = value
        value?.let { binding.accordionDivider.dividerColor = ContextCompat.getColor(context, it) }
    }

    var isExpanded: Boolean = false
    set(value) {
        field = value
        inflaterViewVisibility = if (value) View.VISIBLE else View.GONE
    }

    var inflaterViewVisibility: Int = View.GONE
    set(value) {
        field = value
        binding.accordionInflaterView.visibility = value

        isExpanded = when(binding.accordionInflaterView.visibility){
            View.GONE -> false
            else -> true
        }

        if (binding.accordionInflaterView.visibility != View.GONE){
            binding.accordionInflaterView.layoutParams.height = LayoutParams.WRAP_CONTENT
        }

        binding.accordionIcon.rotation = if (binding.accordionInflaterView.isGone) 0f else 180f

    }

    init {
        setLayout(attrs)
        setClickContainer()
    }

    private fun setLayout(attr: AttributeSet?){
        attr?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.Accordion)

            accordionBackground = attributes.getColorStateList(R.styleable.Accordion_accordionBackground)
            titleText = attributes.getString(R.styleable.Accordion_titleText)
            iconColor = attributes.getColorStateList(R.styleable.Accordion_accordionIconColor)
            dividerColor = attributes.getInteger(R.styleable.Accordion_dividerColor, R.color.white)

            if (accordionBackground == null) accordionBackground = ContextCompat.getColorStateList(context, R.color.light_red)
            if (iconColor == null) iconColor = ContextCompat.getColorStateList(context, R.color.white)

            binding.accordionIcon.setBackgroundColor(Color.TRANSPARENT)

            attributes.recycle()
        }
    }

    private fun changeExpansion(){
        setInflaterViewAnimation()
        animateArrowIcon()
        isExpanded = !isExpanded
    }

    private fun setInflaterViewAnimation(){
        if (isExpanded) animationCollapse(binding.accordionInflaterView)
        else animationExpand(binding.accordionInflaterView)
    }

    private fun animateArrowIcon(){
        if (isExpanded) animateArrowToDown(binding.accordionIcon)
        else animateArrowToUp(binding.accordionIcon)
    }
    private fun setClickContainer(){
        binding.accordionContainer.setOnClickListener {
            changeExpansion()
        }
    }

}