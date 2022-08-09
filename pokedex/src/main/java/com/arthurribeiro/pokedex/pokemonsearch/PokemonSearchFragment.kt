package com.arthurribeiro.pokedex.pokemonsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthurribeiro.foundation.R
import com.arthurribeiro.network.network.Resource
import com.arthurribeiro.pokedex.PokedexViewModel
import com.arthurribeiro.pokedex.databinding.FragmentPokemonSearchBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PokemonSearchFragment : Fragment() {
    private var _binding: FragmentPokemonSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokedexViewModel by sharedViewModel()
    private val args by lazy {
        val args: PokemonSearchFragmentArgs by navArgs()
        args.pokemonList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObserver()
        setActions()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setActions(){
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    private fun setAdapter() {
        val adapter = SearchPokemonListAdapter(
            args.toList(),
            ::setPokemonClick
        )

        binding.searchListRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.searchListRecycler.adapter = adapter
        setSearch(adapter)
    }

    private fun setSearch(adapter: SearchPokemonListAdapter) {
        binding.pokemonSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
    }

    private fun setPokemonClick(id: Int) {
        viewModel.getPokemonDetail(id)
    }

    private fun setObserver() {
        viewModel.pokemonDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> setProgressLoading()

                is Resource.Success -> {
                        findNavController().navigate(
                            PokemonSearchFragmentDirections.pokemonSearchFragmentToDetails(it.data)
                        )
                }
                is Resource.Error.NoConnection -> {
                    dismissProgressLoading()
                    handleErrorFloater(R.string.no_connection_error_message)
                }
                else -> {
                    dismissProgressLoading()
                    handleErrorFloater(R.string.generic_error_message)
                }
            }
        }
    }

    private fun setProgressLoading() {
        with(binding) {
            pokemonListContainer.alpha = 0.5f
            progressLoading.isVisible = true
        }
    }

    private fun dismissProgressLoading(){
        binding.progressLoading.isVisible = false
        binding.pokemonListContainer.alpha = 1f
    }

    private fun handleErrorFloater(stringRes: Int) {
        binding.errorFloater.isVisible = true
        binding.errorFloater.errorText = getString(stringRes)
        binding.errorFloater.isButtonVisible = false
    }
}