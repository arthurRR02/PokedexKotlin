package com.arthurribeiro.foundation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.arthurribeiro.foundation.R
import com.arthurribeiro.foundation.databinding.ViewErrorFloaterBinding

class ErrorFloater @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

        private val binding = ViewErrorFloaterBinding.inflate(LayoutInflater.from(context), this, true)

    var errorText: String? = null
    set(value) {
        field = value
        value?.let { binding.textError.text = it }
    }

    var isButtonVisible: Boolean = true
    set(value) {
        field = value
        binding.tryAgainButton.isVisible = value
    }

    init {
        setLayout(attrs)
        setActions()
    }

    private fun setLayout(attrs: AttributeSet?){
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ErrorFloater)

            errorText = attributes.getString(R.styleable.ErrorFloater_errorText)
            isButtonVisible = attributes.getBoolean(R.styleable.ErrorFloater_isButtonVisible, true)
            attributes.recycle()
        }
    }

    private fun setActions(){
        binding.icClose.setOnClickListener { binding.root.isVisible = false }
    }

    fun setOnTryAgainButtonClick(click: (view: View) -> Unit){
        binding.tryAgainButton.setOnClickListener { view -> click.invoke(view) }
    }
}