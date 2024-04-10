package com.cinetech.domain.usecase

import com.cinetech.domain.models.LoadMovieResponse
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.repository.MovieRepository

class LoadMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(loadMoviesParam: LoadMoviesParam): LoadMovieResponse {
        return movieRepository.loadMovies(loadMoviesParam)
    }
}