package com.cinetech.filmfinder.presentation.screens.movie_search_filter.model


sealed class LoadingCountryFilterUiState {
    class Success(val countries: List<CountryFilterUiState>) : LoadingCountryFilterUiState()
    class Error(val massage: String) : LoadingCountryFilterUiState()
    data object Loading : LoadingCountryFilterUiState()
}