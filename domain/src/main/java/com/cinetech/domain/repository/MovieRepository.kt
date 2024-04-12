package com.cinetech.domain.repository

import com.cinetech.domain.models.LoadMoviesResponse
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.models.Movie
import com.cinetech.domain.models.PossibleValue
import com.cinetech.domain.models.PossibleValuesParam
import com.cinetech.domain.models.SearchMovieResponse
import com.cinetech.domain.models.SearchMoviesParam

interface MovieRepository {
    suspend fun searchMovie(param: SearchMoviesParam): SearchMovieResponse
    suspend fun loadMovies(param: LoadMoviesParam): LoadMoviesResponse
    suspend fun getPossibleValuesByField(param: PossibleValuesParam): List<PossibleValue>
    suspend fun loadMovieByIdUseCase(id: Int): Movie
}