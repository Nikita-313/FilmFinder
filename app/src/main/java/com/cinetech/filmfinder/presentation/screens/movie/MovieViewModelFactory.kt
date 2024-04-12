package com.cinetech.filmfinder.presentation.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cinetech.domain.usecase.LoadCommentsByMovieIdUseCase
import com.cinetech.domain.usecase.LoadMovieByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MovieViewModelFactory @AssistedInject constructor(
    @Assisted private val id: Int,
    private val loadMovieByIdUseCase: LoadMovieByIdUseCase,
    private val loadCommentsByMovieIdUseCase: LoadCommentsByMovieIdUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(
            id = id,
            loadMovieByIdUseCase = loadMovieByIdUseCase,
            loadCommentsByMovieIdUseCase = loadCommentsByMovieIdUseCase
        ) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): MovieViewModelFactory
    }

}