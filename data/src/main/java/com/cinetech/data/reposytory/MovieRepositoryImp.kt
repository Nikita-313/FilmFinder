package com.cinetech.data.reposytory

import com.cinetech.data.network.retrofit.MovieService
import com.cinetech.data.network.retrofit.model.toDomainSearchMovieResponse
import com.cinetech.domain.models.SearchMovieResponse
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.domain.repository.MovieRepository

class MovieRepositoryImp(private val movieService: MovieService) : MovieRepository {

    override suspend fun searchMovie(param: SearchMoviesParam): SearchMovieResponse {
        return movieService.searchMoviesByName(
            name = param.movieName,
            page = param.page,
            limit = param.limitNumber
        ).toDomainSearchMovieResponse()
    }

}