package com.cinetech.domain.repository

import com.cinetech.domain.models.SearchMovieResponse
import com.cinetech.domain.models.SearchMoviesParam

interface MovieRepository {
    suspend fun searchMovie(param: SearchMoviesParam): SearchMovieResponse
}