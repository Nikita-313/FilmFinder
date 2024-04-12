package com.cinetech.filmfinder.presentation.screens.movie_search_filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetech.domain.models.AgeRantingRange
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.models.YearRange
import com.cinetech.domain.usecase.LoadPossibleCountriesUseCase
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.AgeRatingRangeFilterUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.CountryFilterUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.LoadingCountryFilterUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.YearRangeFilterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieSearchFilterViewModel(
    private val loadMoviesParam: LoadMoviesParam,
    private val loadPossibleCountriesUseCase: LoadPossibleCountriesUseCase
) : ViewModel() {

    private val defaultYearRange = YearRangeFilterUiState(from = -1, to = -1)
    private val yearRangeMutableStateFlow = MutableStateFlow(defaultYearRange)
    fun yearRangeStateFlow(): StateFlow<YearRangeFilterUiState> = yearRangeMutableStateFlow

    private val defaultAgeRatingRange = AgeRatingRangeFilterUiState(from = 0, to = 18)
    private val ageRatingRangeMutableStateFlow = MutableStateFlow(defaultAgeRatingRange)
    fun ageRatingRangeStateFlow(): StateFlow<AgeRatingRangeFilterUiState> = ageRatingRangeMutableStateFlow

    private val countryMutableStateFlow = MutableStateFlow<LoadingCountryFilterUiState>(LoadingCountryFilterUiState.Loading)
    fun countryRangeStateFlow(): StateFlow<LoadingCountryFilterUiState> = countryMutableStateFlow
    private var countries: MutableList<CountryFilterUiState> = mutableListOf()
    private var countriesIndex: MutableMap<String, Int> = mutableMapOf()

    init {
        setLoadMoviesParam()
        loadCountriesFromServer()
    }

    private fun setLoadMoviesParam() {
        loadMoviesParam.ageRantingRange?.let {
            updateAgeRatingRange(it.from, it.to)
        }
        loadMoviesParam.yearRange?.let {
            val tmp = YearRange()
            var from = -1
            var to = -1
            if(tmp.from != it.from) from = it.from
            if(tmp.to != it.to) to = it.to

            updateYearRange(from, to)
        }
    }

    fun loadCountriesFromServer() {
        viewModelScope.launch {
            try {
                countryMutableStateFlow.emit(LoadingCountryFilterUiState.Loading)
                val response = loadPossibleCountriesUseCase.execute()

                countries = response.map { CountryFilterUiState(name = it, isSelected = false) }.toMutableList()
                countries.forEachIndexed { i, it -> countriesIndex[it.name] = i }
                loadMoviesParam.countries?.forEach {
                    countriesIndex[it]?.let { i ->
                        countries[i] = countries[i].copy(isSelected = true)
                    }
                }
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

    fun getSearchParam(): LoadMoviesParam {
        var countries: List<String>? = countries.filter { it.isSelected }.map { it.name }
        var ageRantingRange: AgeRantingRange? = null
        var yearRange: YearRange? = null
        val tmpDefaultYearRange = YearRange()

        if (countries?.isEmpty() == true) {
            countries = null
        }

        if (defaultAgeRatingRange != ageRatingRangeMutableStateFlow.value) {
            ageRantingRange = AgeRantingRange(
                from = ageRatingRangeMutableStateFlow.value.from,
                to = ageRatingRangeMutableStateFlow.value.to,
            )
        }
        if (defaultYearRange != yearRangeMutableStateFlow.value) {

            if (yearRangeMutableStateFlow.value.from != -1 && yearRangeMutableStateFlow.value.to != -1) {
                yearRange = tmpDefaultYearRange.copy(from = yearRangeMutableStateFlow.value.from, to = yearRangeMutableStateFlow.value.to)
            }
            else if (yearRangeMutableStateFlow.value.from != -1) {
                yearRange = tmpDefaultYearRange.copy(from = yearRangeMutableStateFlow.value.from)
            }
            else if (yearRangeMutableStateFlow.value.to != -1) {
                yearRange = tmpDefaultYearRange.copy(to = yearRangeMutableStateFlow.value.to)
            }

        }
        println(yearRange)
        return LoadMoviesParam(
            countries = countries,
            ageRantingRange = ageRantingRange,
            yearRange = yearRange
        )
    }
}