package com.cinetech.domain.usecase

import com.cinetech.domain.models.LoadMoviesResponse
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.repository.MovieRepository

class LoadMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(loadMoviesParam: LoadMoviesParam): LoadMoviesResponse {
        return movieRepository.loadMovies(loadMoviesParam)
    }
}