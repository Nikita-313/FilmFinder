package com.cinetech.data.network

import com.cinetech.data.network.model.ApiLoadMoviesResponse
import com.cinetech.data.network.model.ApiPossibleValueDto
import com.cinetech.data.network.model.ApiSearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("/v1.4/movie")
    suspend fun loadMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("countries.name") countriesName: List<String>? = null,
        @Query("ageRating") ageRating:List<String>? = null,
        @Query("year") year:List<String>? = null,
        @Query("selectFields") selectFields: List<String> = arrayListOf("id", "name", "alternativeName", "year", "ageRating", "countries", "poster", "rating")
    ): ApiLoadMoviesResponse

    @GET("/v1.4/movie/search")
    suspend fun searchMoviesByName(
        @Query("query") name: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ApiSearchMovieResponse

    @GET("/v1/movie/possible-values-by-field")
    suspend fun getPossibleValuesByField(@Query("field") field: String): List<ApiPossibleValueDto>

}