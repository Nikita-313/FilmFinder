package com.cinetech.filmfinder.presentation.screens.movie_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cinetech.domain.models.MovieDto
import com.cinetech.filmfinder.R

class MovieSearchRecyclerAdapter : RecyclerView.Adapter<MovieSearchRecyclerAdapter.BaseHolder>() {

    var data: List<MovieDto> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    var loadingIndicator:Boolean = false
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
           // notifyItemChanged(data.size + 1)
        }

    override fun getItemCount(): Int = data.size + 1

    override fun getItemViewType(position: Int) = when {
        position >= data.size -> TYPE_LOADING_INDICATOR
        else -> TYPE_ITEM
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder = when (viewType) {
        TYPE_ITEM -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_preview, parent, false)
            MovieHolder(view)
        }
        TYPE_LOADING_INDICATOR -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_load_indocator, parent, false)
            LoadingIndicatorHolder(view)
        }
        else -> {
            throw RuntimeException("Item type not ${viewType} not supported")
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int)  = when (holder) {

        is MovieHolder-> {
            val movie = data[position]
            holder.movieName.text = movie.name
            holder.movieSubName.text = "${movie.enName}, ${movie.year}"
            holder.movieDescription.text = "${movie.ageRating}+, ${movie.country}"
        }
        is LoadingIndicatorHolder -> {
            if(loadingIndicator) {
                holder.view.visibility = View.VISIBLE
            } else {
                holder.view.visibility = View.GONE
            }
        }
        else ->{}

    }

    abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view)
    class MovieHolder(view: View) : BaseHolder(view) {
        val movieName = view.findViewById<TextView>(R.id.movieName)
        val movieSubName = view.findViewById<TextView>(R.id.movieSubName)
        val movieDescription = view.findViewById<TextView>(R.id.movieDescription)
    }
    class LoadingIndicatorHolder(val view: View) : BaseHolder(view)

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING_INDICATOR = 2
    }
}