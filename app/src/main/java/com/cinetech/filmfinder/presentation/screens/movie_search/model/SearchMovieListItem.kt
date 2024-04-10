package com.cinetech.filmfinder.presentation.screens.movie_search.model

sealed class SearchMovieListItem {
    class SearchQueryItem(val query: String) : SearchMovieListItem()
    class SearchMovieItem(
        val id: Long,
        val movieName: String,
        val movieAlternativeName: String,
        val movieYear: Int,
        val movieRating: Double,
        val preViewUrl: String?,
    ) : SearchMovieListItem()
}