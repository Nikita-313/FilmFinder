package com.cinetech.domain.usecase

import com.cinetech.domain.repository.MovieRepository

class LoadPossibleCountriesUseCase (private val movieRepository: MovieRepository) {

    suspend fun execute(): List<String> {
        return listOf("Россия","США","Беларусь", "Франция", "Китай")
    }

}