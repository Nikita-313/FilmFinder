package com.cinetech.filmfinder.presentation.screens.movie_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cinetech.filmfinder.R
import com.cinetech.filmfinder.databinding.ItemSearchMovieBinding
import com.cinetech.filmfinder.databinding.ItemSearchQueryBinding
import com.cinetech.filmfinder.presentation.screens.movie_search.model.SearchMovieListItem
import java.util.Locale

class SearchRecyclerAdapter(private val onMovieClickListener: (id: Int?, name: String?) -> Unit) : RecyclerView.Adapter<SearchRecyclerAdapter.BaseHolder>() {

    var items: List<SearchMovieListItem> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int) = when (items[position]) {
        is SearchMovieListItem.SearchMovieItem -> {
            TYPE_MOVIE
        }

        is SearchMovieListItem.SearchQueryItem -> {
            TYPE_QUERY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        when (viewType) {
            TYPE_MOVIE -> {
                val binding = ItemSearchMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MovieHolder(binding)
            }

            TYPE_QUERY -> {
                val binding = ItemSearchQueryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return QueryHolder(binding)
            }

            else -> {
                throw RuntimeException("Item type not ${viewType} not supported")
            }
        }
    }

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: BaseHolder, position: Int) = holder.bind(items[position])

    abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: SearchMovieListItem)
    }

    inner class MovieHolder(private val binding: ItemSearchMovieBinding) : BaseHolder(binding.root) {
        override fun bind(item: SearchMovieListItem) {
            if (item !is SearchMovieListItem.SearchMovieItem) return

            binding.apply {
                var description = ""
                if (item.movieAlternativeName.isNotEmpty()) {
                    description += "${item.movieAlternativeName} "
                }
                if (item.movieYear != 0) {
                    description += "${item.movieYear} "
                }

                if (item.preViewUrl != null) {
                    previewImg.load(item.preViewUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_logo_foreground)
                    }
                }

                movieName.text = item.movieName
                movieDescription.text = description

                if (description.isEmpty()) {
                    movieDescription.visibility = View.GONE
                }

                item.movieRating?.let {
                    movieRating.text = String.format(Locale.US, "%.1f", item.movieRating)
                }
            }

        }

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition;
                if(position != RecyclerView.NO_POSITION) {
                    val item = items[position]
                    if(item is SearchMovieListItem.SearchMovieItem) onMovieClickListener(item.id.toInt(),item.movieName)
                }
            }
        }
    }

    class QueryHolder(private val binding: ItemSearchQueryBinding) : BaseHolder(binding.root) {
        override fun bind(item: SearchMovieListItem) {
            if (item !is SearchMovieListItem.SearchQueryItem) return

            binding.apply {
                query.text = item.query
            }
        }
    }

    companion object {
        private const val TYPE_MOVIE = 1
        private const val TYPE_QUERY = 2
    }
}