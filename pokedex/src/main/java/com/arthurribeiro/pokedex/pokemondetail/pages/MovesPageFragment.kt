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
import com.arthurribeiro.foundation.utils.IntentHelper
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.model.model.MoveDTO
import com.arthurribeiro.moves.MovesMainActivity
import com.arthurribeiro.pokedex.databinding.FragmentMovesPageBinding
import com.arthurribeiro.foundation.R as foundationResources

class MovesPageFragment(private val pokemonMoves: List<MoveDTO>?) : Fragment() {

    private var _binding: FragmentMovesPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovesPageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMovesText()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createMovesText() {
        pokemonMoves?.forEach {
            val textView = TextView(requireContext()).apply {
                val moveText = SpannableString(it.move?.name?.firstCharUpperCase())
                it.move?.name?.length?.let { length ->
                    moveText.setSpan(UnderlineSpan(), 0, length, 0)
                }
                text = moveText
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
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

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val horizontalMargins = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    8f,
                    resources.displayMetrics
                )
                val verticalMargins = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16f,
                    resources.displayMetrics
                )
                params.setMargins(
                    horizontalMargins.toInt(),
                    verticalMargins.toInt(),
                    horizontalMargins.toInt(),
                    verticalMargins.toInt()
                )
                layoutParams = params

                setOnClickListener { _ ->
                    val intent = Intent(requireContext(), MovesMainActivity::class.java).apply {
                        putExtra(IntentHelper.MOVE_URL, it.move?.url)
                    }
                    startActivity(intent)
                }
            }
            binding.llContainerMoves.addView(textView)
        }
    }
}