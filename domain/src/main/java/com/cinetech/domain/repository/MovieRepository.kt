package com.cinetech.domain.repository

import com.cinetech.domain.models.LoadMovieResponse
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.models.SearchMovieResponse
import com.cinetech.domain.models.SearchMoviesParam

interface MovieRepository {
    suspend fun searchMovie(param: SearchMoviesParam): SearchMovieResponse
    suspend fun loadMovies(param: LoadMoviesParam): LoadMovieResponse
}