package com.cinetech.domain.usecase

import com.cinetech.domain.models.PossibleValuesParam
import com.cinetech.domain.repository.MovieRepository

class LoadPossibleCountriesUseCase (private val movieRepository: MovieRepository) {

    suspend fun execute(): List<String> {
        return movieRepository.getPossibleValuesByField(PossibleValuesParam(field = "countries.name")).mapNotNull { it.name }
    }

}