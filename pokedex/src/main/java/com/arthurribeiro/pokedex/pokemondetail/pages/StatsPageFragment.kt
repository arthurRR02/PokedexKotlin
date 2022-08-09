package com.arthurribeiro.pokedex.pokemondetail.pages

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.model.model.StatsDTO
import com.arthurribeiro.pokedex.databinding.FragmentStatsPageBinding
import com.arthurribeiro.foundation.R as foundationResources

class StatsPageFragment(private val pokemonStats: List<StatsDTO>?) : Fragment() {

    private var _binding: FragmentStatsPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsPageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createLabels()
        createValues()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createLabels() {
        pokemonStats?.forEach {
            val textView = TextView(requireContext()).apply {
                it.stat?.name?.length?.let { length ->
                    text =
                        if (length > 2) "${it.stat?.name.toString().firstCharUpperCase()}: "
                        else "${it.stat?.name.toString().uppercase()}: "
                }
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setTextColor(ContextCompat.getColor(requireContext(), foundationResources.color.white))
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
            }
            binding.llStatsContainerLabel.addView(textView)
        }
    }

    private fun createValues() {
        pokemonStats?.forEach {
            val textView = TextView(requireContext()).apply {
                text = it.baseStat.toString()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setTextColor(ContextCompat.getColor(requireContext(), foundationResources.color.white))
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
            }
            binding.llStatsContainerValue.addView(textView)
        }
    }
}