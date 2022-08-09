package com.arthurribeiro.pokedex.pokemonsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.pokedex.databinding.ItemPokemonSearchBinding
import com.arthurribeiro.model.model.PokemonDTO

class SearchPokemonListAdapter(
    private val pokemonList: List<PokemonDTO>? = null,
    private val onClickCallback: ((id: Int) -> Unit)? = null
) :
    RecyclerView.Adapter<SearchPokemonListAdapter.SearchPokemonListViewHolder>(), Filterable {

    private var filteredListPokemon: List<PokemonDTO>? = pokemonList

    inner class SearchPokemonListViewHolder(private val binding: ItemPokemonSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun getId(): Int{
            var id = 0
             if (filteredListPokemon != pokemonList)
                pokemonList?.forEachIndexed { index, pokemonDTO ->
                if (pokemonDTO.name == filteredListPokemon?.get(absoluteAdapterPosition)?.name) id = index + 1
            } else id = absoluteAdapterPosition + 1
            return id
        }

        fun bind(pokemon: PokemonDTO) {
            with(binding) {
                pokemonName.text = pokemon.name?.firstCharUpperCase()
                pokemonNameContainer.setOnClickListener {
                    onClickCallback?.invoke(getId())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPokemonListViewHolder {
        val binding =
            ItemPokemonSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchPokemonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchPokemonListViewHolder, position: Int) {
        val item = filteredListPokemon?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = filteredListPokemon?.size ?: 0

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(char: CharSequence?): FilterResults {
                val charString = char.toString()
                filteredListPokemon = if (charString.isEmpty()) {
                    pokemonList
                } else {
                    val result: MutableList<PokemonDTO> = mutableListOf()
                    pokemonList?.let {
                        it.forEach { pokemonDTO ->
                            if (pokemonDTO.name?.lowercase()
                                    ?.contains(charString.lowercase()) == true
                            ) result.add(pokemonDTO)
                        }
                    }
                    result
                }
                val resultFiltered = FilterResults()
                resultFiltered.values = filteredListPokemon
                return resultFiltered
            }

            override fun publishResults(char: CharSequence?, results: FilterResults?) {
                filteredListPokemon = results?.values as List<PokemonDTO>
                notifyDataSetChanged()
            }

        }
    }
}