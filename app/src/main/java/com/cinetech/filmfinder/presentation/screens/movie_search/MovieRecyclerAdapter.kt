package com.cinetech.filmfinder.presentation.screens.movie_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cinetech.domain.models.PreviewMovie
import com.cinetech.filmfinder.R
import java.util.Locale

class MovieRecyclerAdapter : RecyclerView.Adapter<MovieRecyclerAdapter.BaseHolder>() {

    var data: List<PreviewMovie> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    var loadingIndicator: Boolean = false
        set(newValue) {
            field = newValue
            notifyItemChanged(data.size)
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

        else -> throw RuntimeException("Item type: $viewType. Not supported")

    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) = when (holder) {

        is MovieHolder -> {
            val movie = data[position]
            var movieSubName = ""
            var description = ""

            if (!movie.alternativeName.isNullOrEmpty()) movieSubName += "${movie.alternativeName} "
            if (movie.year != null) movieSubName += "${movie.year}"
            if (movie.countries.isNotEmpty()) {
                movie.countries.forEachIndexed { i, country ->
                    if (i != movie.countries.size - 1) description += "$country, "
                    else description += "$country • "
                }
            }
            if (movie.ageRating != null) description += "${movie.ageRating}+"
            if (!movie.preViewUrl.isNullOrEmpty()) {
                holder.preViewImage.load(movie.preViewUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_logo_foreground)
                }
            }

            holder.movieName.text = movie.name
            holder.movieSubName.text = movieSubName
            holder.movieDescription.text = description
            holder.kpRating.text = String.format(Locale.US, "%.1f", movie.kpRating);
        }

        is LoadingIndicatorHolder -> {
            if (loadingIndicator) {
                holder.view.visibility = View.VISIBLE
            } else {
                holder.view.visibility = View.GONE
            }
        }

        else -> throw RuntimeException("Item type: $holder. Not supported")

    }

    abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view)
    class MovieHolder(view: View) : BaseHolder(view) {
        val movieName: TextView = view.findViewById(R.id.movieName)
        val movieSubName: TextView = view.findViewById(R.id.movieSubName)
        val movieDescription: TextView = view.findViewById(R.id.movieDescription)
        val preViewImage: ImageView = view.findViewById(R.id.previewImg)
        val kpRating: TextView = view.findViewById(R.id.kpRating)
    }

    class LoadingIndicatorHolder(val view: View) : BaseHolder(view)

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING_INDICATOR = 2
    }
}