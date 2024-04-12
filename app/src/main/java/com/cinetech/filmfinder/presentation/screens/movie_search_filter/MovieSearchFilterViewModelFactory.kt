package com.cinetech.filmfinder.presentation.screens.movie_search_filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.usecase.LoadPossibleCountriesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class MovieSearchFilterViewModelFactory @AssistedInject constructor(
    @Assisted private val loadMoviesParam: LoadMoviesParam,
    private val loadPossibleCountriesUseCase: LoadPossibleCountriesUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieSearchFilterViewModel(
            loadMoviesParam = loadMoviesParam,
            loadPossibleCountriesUseCase = loadPossibleCountriesUseCase
        ) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(loadMoviesParam: LoadMoviesParam): MovieSearchFilterViewModelFactory
    }

}