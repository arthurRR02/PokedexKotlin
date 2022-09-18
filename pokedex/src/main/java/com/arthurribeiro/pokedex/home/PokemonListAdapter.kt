package com.arthurribeiro.pokedex.home

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.foundation.utils.getImageFromUrl
import com.arthurribeiro.foundation.utils.setPokemonId
import com.arthurribeiro.pokedex.PokedexViewModel
import com.arthurribeiro.pokedex.databinding.ItemPokemonBinding
import com.arthurribeiro.model.model.PokemonDTO

class PokemonListAdapter(
    private val pokemonList: List<PokemonDTO>? = null,
    private val activity: Activity? = null,
    private val clickCallback: ((id: Int) -> Unit)? = null,
    private val isLoading: Boolean = true,
    private val viewModel: PokedexViewModel? = null
) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>(){

    inner class PokemonListViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonDTO: PokemonDTO) {
            val id = absoluteAdapterPosition + 1
            val url = viewModel?.getUrl(id)

            with(binding) {
                if (isLoading) {
                    pokemonCard.isVisible = false
                    shimmer.startShimmer()
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        shimmer.stopShimmer()
                        shimmer.hideShimmer()
                        pokemonCard.isVisible = true
                    }, 3000)

                    pokemonCard.setOnClickListener { clickCallback?.invoke(id) }

                    pokemonName.text = pokemonDTO.name?.firstCharUpperCase()
                    pokemonNumber.setPokemonId(id)
                    if (activity != null) {
                        url?.let {
                            pokemonImage.getImageFromUrl(activity, it) { drawable ->
                                pokemonImage.setImageDrawable(drawable)
                            }
                        }
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val pokemon = pokemonList?.get(position)
        if (pokemon != null) holder.bind(pokemon)
    }

    override fun getItemCount(): Int = pokemonList?.size ?: 0
}