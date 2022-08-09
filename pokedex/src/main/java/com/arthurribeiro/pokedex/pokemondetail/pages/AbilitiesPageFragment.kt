package com.arthurribeiro.pokedex.pokemondetail.pages

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arthurribeiro.abilities.AbilitiesMainActivity
import com.arthurribeiro.foundation.utils.IntentHelper
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.model.model.AbilityDTO
import com.arthurribeiro.pokedex.databinding.FragmentAbilitiesPageBinding
import com.arthurribeiro.foundation.R as foundationResources

class AbilitiesPageFragment(private val pokemonAbilities: List<AbilityDTO>) : Fragment() {

    private var _binding: FragmentAbilitiesPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbilitiesPageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAbilitiesText()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createAbilitiesText() {
        pokemonAbilities.forEach {
            val textView = TextView(requireContext()).apply {
                val abilityText = SpannableString(it.ability.name?.firstCharUpperCase())
                it.ability.name?.length?.let { length ->
                    abilityText.setSpan(UnderlineSpan(), 0, length, 0)
                }
                text = abilityText
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        foundationResources.color.white
                    )
                )

                val drawable = ContextCompat.getDrawable(
                    requireContext(),
                    foundationResources.drawable.ic_keyboard_arrow_right
                )
                drawable?.setTintList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        foundationResources.color.white
                    )
                )
                setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )

                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val horizontalMargin = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    8f,
                    resources.displayMetrics
                )
                val verticalMargin = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16f,
                    resources.displayMetrics
                )
                params.setMargins(
                    horizontalMargin.toInt(),
                    verticalMargin.toInt(),
                    horizontalMargin.toInt(),
                    verticalMargin.toInt()
                )
                layoutParams = params

                setOnClickListener { _ ->
                    val intent = Intent(requireContext(), AbilitiesMainActivity::class.java).apply {
                        putExtra(IntentHelper.ABILITY_URL, it.ability.url)
                    }
                    startActivity(intent)
                }
            }
            binding.llContainerAbilities.addView(textView)
        }
    }
}