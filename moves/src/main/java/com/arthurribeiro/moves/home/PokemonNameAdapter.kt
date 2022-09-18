package com.arthurribeiro.moves.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthurribeiro.model.model.PokemonDTO
import com.arthurribeiro.moves.databinding.PokemonAccordionItemBinding

class PokemonNameAdapter(private val pokemonList: List<PokemonDTO>? = null) : RecyclerView.Adapter<PokemonNameAdapter.PokemonNameViewHolder>() {

    inner class PokemonNameViewHolder(private val binding: PokemonAccordionItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon: PokemonDTO?){
            binding.pokemonName.text = pokemon?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonNameViewHolder {
        val binding = PokemonAccordionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonNameViewHolder, position: Int) {
        val item = pokemonList?.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int = pokemonList?.size ?: 0
}