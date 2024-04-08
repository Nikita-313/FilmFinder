package com.cinetech.domain.usecase


import com.cinetech.domain.models.SearchMovieResponse
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.domain.repository.MovieRepository

class SearchMoviesByNameUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(param: SearchMoviesParam): SearchMovieResponse {
        return movieRepository.searchMovie(param)
    }

}