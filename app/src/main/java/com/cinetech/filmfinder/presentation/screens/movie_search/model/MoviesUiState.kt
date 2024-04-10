package com.cinetech.filmfinder.presentation.screens.movie_search.model

import com.cinetech.domain.models.PreviewMovie

sealed class MoviesUiState {
    class Success(val movies: List<PreviewMovie>) : MoviesUiState()
    class Error(val massage: String) : MoviesUiState()
    data object Loading : MoviesUiState()
}