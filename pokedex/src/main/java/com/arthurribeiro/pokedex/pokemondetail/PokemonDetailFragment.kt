package com.arthurribeiro.pokedex.pokemondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.arthurribeiro.foundation.utils.*
import com.arthurribeiro.pokedex.PokedexViewModel
import com.arthurribeiro.pokedex.R
import com.arthurribeiro.pokedex.databinding.FragmentPokemonDetailBinding
import com.arthurribeiro.pokedex.pokemondetail.pages.AbilitiesPageFragment
import com.arthurribeiro.pokedex.pokemondetail.pages.DetailsPageFragment
import com.arthurribeiro.pokedex.pokemondetail.pages.MovesPageFragment
import com.arthurribeiro.pokedex.pokemondetail.pages.StatsPageFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.arthurribeiro.foundation.R as foundationResources

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokedexViewModel by sharedViewModel()
    private val pokemonDetail by lazy {
        val args: PokemonDetailFragmentArgs by navArgs()
        args.pokemonDetail
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
        onBackPressed()
        setViews()
        setPageAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startAnimation() {
        with(binding) {
            val type = pokemonDetail.types?.first()?.type?.name
            val colorType = type?.let { getColorByType(it) }
            colorType?.let {
                root.setColorAnimation(foundationResources.color.light_red, it, requireContext())
            }

            val fadeIn = ScaleAnimation(
                0f,
                1f,
                0f,
                1f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )

            fadeIn.duration = 1000
            fadeIn.fillAfter = true
            imagePokemon.startAnimation(fadeIn)
        }
    }

    private fun onBackPressed() {
        binding.header.setOnLeftIconClickListener {
            handleBackPressed()
        }
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    private fun handleBackPressed() {
        if (isAdded) findNavController().popBackStack()
    }

    private fun setViews() {
        with(binding) {
            pokemonDetail.id?.let { viewModel.getUrl(it) }?.let {
                imagePokemon.getImageFromUrl(requireActivity(), it) { drawable ->
                    imagePokemon.setImageDrawable(drawable)
                }
            }

            pokemonDetail.id?.let { textPokemonId.setPokemonId(it) }
            textPokemonName.text = pokemonDetail.name?.firstCharUpperCase()
        }
    }

    private fun setPageAdapter() {
        with(binding) {
            val fragmentList = mutableListOf(
                DetailsPageFragment(pokemonDetail),
                StatsPageFragment(pokemonDetail.stats),
                MovesPageFragment(pokemonDetail.moves),
                AbilitiesPageFragment(pokemonDetail.abilities)
            )
            val adapter = PokemonDetailPageAdapter(childFragmentManager, lifecycle, fragmentList)
            informationPages.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            informationPages.adapter = adapter

            TabLayoutMediator(tabLayoutPokemonInformation, informationPages){ tab, position ->
                when(position){
                    0 -> tab.text = getString(R.string.details_tab_label)
                    1 -> tab.text = getString(R.string.stats_tab_label)
                    2 -> tab.text = getString(R.string.moves_tab_label)
                    3 -> tab.text = getString(R.string.abilities_tab_label)
                }
            }.attach()
        }
    }
}