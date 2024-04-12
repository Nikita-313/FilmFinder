package com.cinetech.filmfinder.presentation.screens.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cinetech.domain.models.LinkedMovie
import com.cinetech.filmfinder.R
import com.cinetech.filmfinder.databinding.ItemPosterPreviewBinding
import java.util.Locale

class PosterAdapter : RecyclerView.Adapter<PosterAdapter.ItemHolder>() {


    var items: List<LinkedMovie> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ItemPosterPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position % items.size])
    }

    class ItemHolder(private val binding: ItemPosterPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LinkedMovie) {
            binding.apply {
                image.load(item.preViewUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_logo_foreground)
                }
                item.kpRating?.let {
                    kpRating.text = String.format(Locale.US, "%.1f", it)
                }
                item.name?.let {
                    movieName.text = it
                }
                item.year?.let {
                    movieDescription.text = it.toString()
                }
            }
        }
    }
}