package com.cinetech.filmfinder.presentation.screens.movie_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cinetech.domain.usecase.LoadMoviesUseCase
import com.cinetech.domain.usecase.SearchMoviesByNameUseCase
import javax.inject.Inject

class MovieSearchFragmentFactory @Inject constructor(
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase,
    private val loadMoviesUseCase: LoadMoviesUseCase,
) :ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(
            searchMoviesByNameUseCase = searchMoviesByNameUseCase,
            loadMoviesUseCase = loadMoviesUseCase,
        ) as T
    }
}