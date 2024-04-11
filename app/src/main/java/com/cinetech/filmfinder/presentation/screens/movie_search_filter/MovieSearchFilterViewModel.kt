package com.cinetech.filmfinder.presentation.screens.movie_search_filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetech.domain.usecase.LoadPossibleCountriesUseCase
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.AgeRatingRangeFilterUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.CountryFilterUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.LoadingCountryFilterUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.YearRangeFilterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieSearchFilterViewModel(
    private val loadPossibleCountriesUseCase: LoadPossibleCountriesUseCase
) : ViewModel() {

    private val yearRangeMutableStateFlow = MutableStateFlow(YearRangeFilterUiState(from = -1, to = -1))
    fun yearRangeStateFlow(): StateFlow<YearRangeFilterUiState> = yearRangeMutableStateFlow

    private val ageRatingRangeMutableStateFlow = MutableStateFlow(AgeRatingRangeFilterUiState(from = 0, to = 21))
    fun ageRatingRangeStateFlow(): StateFlow<AgeRatingRangeFilterUiState> = ageRatingRangeMutableStateFlow

    private val countryMutableStateFlow = MutableStateFlow<LoadingCountryFilterUiState>(LoadingCountryFilterUiState.Loading)
    fun countryRangeStateFlow(): StateFlow<LoadingCountryFilterUiState> = countryMutableStateFlow
    private var countries: MutableList<CountryFilterUiState> = mutableListOf()

    init {
        loadCountriesFromServer()
    }

    fun loadCountriesFromServer() {
        viewModelScope.launch {
            try {
                countryMutableStateFlow.emit(LoadingCountryFilterUiState.Loading)
                val response = loadPossibleCountriesUseCase.execute()
                countries = response.map { CountryFilterUiState(name = it, isSelected = false) }.toMutableList()

                countryMutableStateFlow.emit(LoadingCountryFilterUiState.Success(countries))

            } catch (e: Exception) {
                countryMutableStateFlow.emit(LoadingCountryFilterUiState.Error(e.toString()))
            }
        }
    }


    fun updateAgeRatingRange(from: Int, to: Int) {
        ageRatingRangeMutableStateFlow.tryEmit(ageRatingRangeMutableStateFlow.value.copy(from = from, to = to));
    }

    fun updateYearRange(from: Int, to: Int) {
        yearRangeMutableStateFlow.tryEmit(yearRangeMutableStateFlow.value.copy(from = from, to = to));
    }

    fun updateSelectedCountries(index: Int, state: Boolean) {
        countries[index] = countries[index].copy(isSelected = state)
        countryMutableStateFlow.tryEmit(LoadingCountryFilterUiState.Success(countries))
    }

    fun uncheckAllSelectedCountries() {
        countries.forEachIndexed { i, country ->
            if (country.isSelected) {
                countries[i] = countries[i].copy(isSelected = false)
            }
        }
        countryMutableStateFlow.tryEmit(LoadingCountryFilterUiState.Success(countries))
    }


}