package com.cinetech.filmfinder.presentation.screens.movie_search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cinetech.domain.models.MovieDto
import com.cinetech.filmfinder.databinding.ItemSearchMovieBinding

class MovieSearchRecyclerAdapter(
    private val context: Context
) : RecyclerView.Adapter<MovieSearchRecyclerAdapter.ViewHolder>() {

    var data: List<MovieDto> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieSearchRecyclerAdapter.ViewHolder {
        val binding = ItemSearchMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: MovieSearchRecyclerAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            val movie = data[position]
            movieName.text = movie.name
            movieSubName.text = "${movie.enName}, ${movie.year}"
            movieDescription.text = "${movie.ageRating}+, ${movie.country}"
        }
    }


    class ViewHolder(val binding: ItemSearchMovieBinding) : RecyclerView.ViewHolder(binding.root)

}