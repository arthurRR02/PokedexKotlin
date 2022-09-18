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
    private var _isExpanded: Boolean = false

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

    var iconColor: Int? = null
    set(value) {
        field = value
        value?.let { binding.accordionIcon.setBackgroundColor(it) }
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
        _isExpanded = isExpanded
    }
    get() = _isExpanded

    var inflaterViewVisibility: Int = View.GONE
    set(value) {
        field = value
        binding.accordionInflaterView.visibility = value

        this._isExpanded = when(binding.accordionInflaterView.visibility){
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
            iconColor = attributes.getInteger(R.styleable.Accordion_accordionIconColor, R.color.white)
            dividerColor = attributes.getInteger(R.styleable.Accordion_divColor, R.color.white)

            if (accordionBackground == null) accordionBackground = ContextCompat.getColorStateList(context, R.color.light_red)

            binding.accordionIcon.setBackgroundColor(Color.TRANSPARENT)

            attributes.recycle()
        }
    }

    private fun changeExpansion(){
        setInflaterViewAnimation()
        animateArrowIcon()
        this._isExpanded = !this._isExpanded
    }

    private fun setInflaterViewAnimation(){
        if (this._isExpanded) animationCollapse(binding.accordionInflaterView)
        else animationExpand(binding.accordionInflaterView)
    }

    private fun animateArrowIcon(){
        if (this._isExpanded) animateArrowToDown(binding.accordionIcon)
        else animateArrowToUp(binding.accordionIcon)
    }
    private fun setClickContainer(){
        binding.accordionContainer.setOnClickListener {
            changeExpansion()
        }
    }

    fun addInflaterView(view: View?) {
        binding.accordionInflaterView.apply {
            this.addView(view)
        }
    }

}