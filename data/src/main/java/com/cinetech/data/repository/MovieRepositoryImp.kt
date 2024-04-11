package com.cinetech.data.repository

import com.cinetech.data.network.MovieService
import com.cinetech.data.network.model.toDomainLoadMovieResponse
import com.cinetech.data.network.model.toDomainPossibleValue
import com.cinetech.data.network.model.toDomainSearchMovieResponse
import com.cinetech.domain.models.LoadMovieResponse
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.models.PossibleValue
import com.cinetech.domain.models.PossibleValuesParam
import com.cinetech.domain.models.SearchMovieResponse
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.domain.repository.MovieRepository

class MovieRepositoryImp(private val movieService: MovieService) : MovieRepository {

    override suspend fun searchMovie(param: SearchMoviesParam): SearchMovieResponse {
        return movieService.searchMoviesByName(
            name = param.movieName,
            page = param.page,
            limit = param.limitNumber,
        ).toDomainSearchMovieResponse()
    }

    override suspend fun loadMovies(param: LoadMoviesParam): LoadMovieResponse {
        return movieService.loadMovies(
            page = param.page,
            limit = param.limitNumber,
            countriesName = param.countries,
            ageRating = param.ageRantingRange?.let { listOf("${it.from}-${it.to}") },
            year = param.yearRange?.let { listOf("${it.from}-${it.to}") }
        ).toDomainLoadMovieResponse()
    }

    override suspend fun getPossibleValuesByField(param: PossibleValuesParam): List<PossibleValue> {
        return movieService.getPossibleValuesByField(
            field = param.field
        ).map { it.toDomainPossibleValue() }
    }
}
