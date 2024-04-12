package com.cinetech.domain.usecase

import com.cinetech.domain.models.Movie
import com.cinetech.domain.repository.MovieRepository

class LoadMovieByIdUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(id: Int): Movie {
        return movieRepository.loadMovieByIdUseCase(id)
    }
}