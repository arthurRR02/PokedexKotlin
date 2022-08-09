package com.arthurribeiro.pokedex.pokemondetail.pages

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arthurribeiro.foundation.utils.decimetresToCentimeters
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.foundation.utils.getColorByType
import com.arthurribeiro.foundation.utils.hectogramsToKilograms
import com.arthurribeiro.model.model.PokemonDetailDTO
import com.arthurribeiro.pokedex.databinding.FragmentDetailsPageBinding
import com.arthurribeiro.foundation.R as foundationResources

class DetailsPageFragment(private val pokemonDetail: PokemonDetailDTO) : Fragment() {

    private var _binding: FragmentDetailsPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsPageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setViews() {
        with(binding) {
            textHeight.text = pokemonDetail.height?.decimetresToCentimeters()
            textWeight.text = pokemonDetail.weight?.hectogramsToKilograms()

            pokemonDetail.types?.forEach { it ->
                val textType = TextView(requireContext()).apply {
                    text = it.type?.name?.firstCharUpperCase()
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                    val params = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
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

                    setTextColor(ContextCompat.getColor(requireContext(), foundationResources.color.white))


                    val colorType = it.type?.name?.let { type -> getColorByType(type) }
                    val backGroundDrawable =
                        ContextCompat.getDrawable(requireContext(), foundationResources.drawable.types_bg)
                    background = backGroundDrawable

                    backgroundTintList = colorType?.let { color ->
                        ContextCompat.getColorStateList(
                            requireContext(),
                            color
                        )
                    }
                }
                typesContainer.addView(textType)
            }
        }
    }
}