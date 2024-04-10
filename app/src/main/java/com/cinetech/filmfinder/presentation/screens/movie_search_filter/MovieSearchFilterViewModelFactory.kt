package com.cinetech.filmfinder.presentation.screens.movie_search_filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cinetech.domain.usecase.LoadPossibleCountriesUseCase
import javax.inject.Inject

class MovieSearchFilterViewModelFactory @Inject constructor(
    private val loadPossibleCountriesUseCase: LoadPossibleCountriesUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieSearchFilterViewModel(
            loadPossibleCountriesUseCase = loadPossibleCountriesUseCase
        ) as T
    }

}