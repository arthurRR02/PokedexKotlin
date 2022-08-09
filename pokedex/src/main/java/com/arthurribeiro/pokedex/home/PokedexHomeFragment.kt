package com.arthurribeiro.pokedex.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurribeiro.foundation.R
import com.arthurribeiro.model.model.PokemonDTO
import com.arthurribeiro.network.network.Resource
import com.arthurribeiro.pokedex.PokedexViewModel
import com.arthurribeiro.pokedex.databinding.FragmentPokedexHomeBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PokedexHomeFragment : Fragment() {

    private var _binding: FragmentPokedexHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokedexViewModel by sharedViewModel()
    private lateinit var adapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemons()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setAdapter(isLoading = true)
                }
                is Resource.Success -> {
                    binding.errorFloater.isVisible = false
                    setAdapter(it.data.results, false)
                }
                is Resource.Error.NoConnection -> {
                    handleErrorFloater(false, R.string.no_connection_error_message){
                        viewModel.getPokemons()
                    }
                }
                else -> {
                    handleErrorFloater(true, R.string.generic_error_message){
                        viewModel.getPokemons()
                    }
                }
            }
        }
        viewModel.pokemonDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> setProgressLoading()

                is Resource.Success -> {
                    binding.errorFloater.isVisible = false
                        findNavController().navigate(
                            PokedexHomeFragmentDirections.pokemonHomeFragmentToDetails(it.data)
                        )
                }
                is Resource.Error.NoConnection -> {
                    dismissProgressLoading()
                    handleErrorFloater(false, R.string.no_connection_error_message)
                }
                else -> {
                    dismissProgressLoading()
                    handleErrorFloater(false, R.string.generic_error_message)
                }
            }
        }
    }

    private fun setAdapter(pokemonList: List<PokemonDTO>? = null, isLoading: Boolean) {
        with(binding) {
            adapter = PokemonListAdapter(
                pokemonList,
                activity = requireActivity(),
                isLoading = isLoading,
                clickCallback = ::pokemonCardClick,
                viewModel = viewModel
            )
            adapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            pokemonRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
            pokemonRecycler.adapter = adapter
            pokemonRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    btnUpward.isVisible = dy > 0
                }


            })

            btnUpward.setOnClickListener { pokemonRecycler.smoothScrollToPosition(0) }
            searchPokemon(pokemonList)
        }
    }


    private fun searchPokemon(pokemonList: List<PokemonDTO>? = null) {
        binding.pokemonSearch.setOnClickListener {
            pokemonList?.toTypedArray()?.let { list ->
                findNavController().navigate(
                    PokedexHomeFragmentDirections.pokemonHomeFragmentToSearch(
                        list
                    )
                )
            }

        }
    }

    private fun pokemonCardClick(id: Int) {
        viewModel.getPokemonDetail(id)
    }

    private fun setProgressLoading() {
        with(binding) {
            pokemonListContainer.alpha = 0.5f
            progressLoading.isVisible = true
        }
    }

    private fun dismissProgressLoading() {
        with(binding) {
            progressLoading.isVisible = false
            pokemonListContainer.alpha = 1f
        }
    }

    private fun handleErrorFloater(
        isButtonVisible: Boolean = true,
        stringRes: Int,
        buttonClick: (() -> Unit)? = null
    ) {
        with(binding) {
            errorFloater.isVisible = true
            errorFloater.errorText = getString(stringRes)
            errorFloater.isButtonVisible = isButtonVisible
            errorFloater.setOnTryAgainButtonClick { buttonClick?.invoke() }
        }
    }

}