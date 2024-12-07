package com.arthurribeiro.abilities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthurribeiro.abilities.AbilitiesViewModel
import com.arthurribeiro.abilities.R
import com.arthurribeiro.abilities.databinding.FragmentAbilitiesHomeBinding
import com.arthurribeiro.abilities.databinding.InfoModalBinding
import com.arthurribeiro.abilities.databinding.PokemonsAccordionBinding
import com.arthurribeiro.foundation.utils.IntentHelper
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.foundation.views.Modal
import com.arthurribeiro.model.model.AbilityDetailDTO
import com.arthurribeiro.model.model.AbilityPokemonDTO
import com.arthurribeiro.model.model.PokemonDTO
import com.arthurribeiro.network.network.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.arthurribeiro.foundation.R as foundationRes

class AbilitiesHomeFragment : Fragment() {

    private var _binding: FragmentAbilitiesHomeBinding? = null
    private val binding get() = _binding!!

    private var _accordionBinding: PokemonsAccordionBinding? = null
    private val accordionBinding get() = _accordionBinding!!

    private val viewModel: AbilitiesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbilitiesHomeBinding.inflate(inflater, container, false)
        _accordionBinding = PokemonsAccordionBinding.inflate(inflater, null, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(IntentHelper.ABILITY_URL)
        with(viewModel) {
            setAbilityUrl(url)
            getAbilityUrl()?.let { getAbilitiesDetail(it) }
        }
        setObservers()
        setActions()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setObservers() {
        viewModel.abilitiesDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    dismissProgressBar()
                    setViews(it.data)
                }
                is Resource.Error.NoConnection -> {
                    handleErrorFloater(foundationRes.string.no_connection_error_message)
                }
                else -> {
                    handleErrorFloater(foundationRes.string.generic_error_message)
                }
            }
        }
    }

    private fun setViews(abilityDetail: AbilityDetailDTO) {
        with(binding) {
            abilitiesHomeTitle.text = abilityDetail.name
            movesHomeSubtitle.text = abilityDetail.effectEntries?.first()?.effect
            abilitiesGeneration.text = abilityDetail.generation?.name
            abilitiesAccordion.apply {
                addInflaterView(accordionBinding.root)
            }
            setAdapter(abilityDetail.pokemon)

            abilitiesGenerationLabel.setOnIconClick {
                abilityDetail.generation?.name?.firstCharUpperCase()?.let { name ->
                    showModal(
                        name,
                        getString(R.string.abilities_generation_modal_description),
                        "generation"
                    )
                }
            }
        }
    }

    private fun setAdapter(pokemonList: List<AbilityPokemonDTO>? = null) {
        with(accordionBinding) {
            val adapter = PokemonNameAdapter(pokemonList)
            accordionRecycler.layoutManager = LinearLayoutManager(requireContext())
            accordionRecycler.adapter = adapter
        }
    }

    private fun showModal(title: String, description: String, tag: String) {
        val modalBinding =
            InfoModalBinding.inflate(LayoutInflater.from(context), binding.root, false)
        val modal = Modal(modalBinding.root)

        modalBinding.movesModalTitle.text = title
        modalBinding.movesModalDescription.text = description
        modalBinding.movesModalIcClose.setOnClickListener { modal.dismissAllowingStateLoss() }

        modal.show(childFragmentManager, tag)
    }

    private fun showProgressBar() {
        binding.abilitiesContainer.alpha = 0.5f
        binding.abilitiesProgressBar.isVisible = true
    }

    private fun dismissProgressBar() {
        binding.abilitiesContainer.alpha = 1.0f
        binding.abilitiesProgressBar.isVisible = false
    }

    private fun handleErrorFloater(stringRes: Int) {
        dismissProgressBar()
        with(binding) {
            errorFloater.isVisible = true
            errorFloater.errorText = getString(stringRes)
            errorFloater.isButtonVisible = true
            errorFloater.setOnTryAgainButtonClick {
                viewModel.getAbilityUrl()?.let { url -> viewModel.getAbilitiesDetail(url) }
                errorFloater.isVisible = false
            }
        }
    }

    private fun setActions() {
        with(binding) {
            abilitiesHomeHeader.setOnRightIconClickListener { requireActivity().finish() }
        }
    }
}