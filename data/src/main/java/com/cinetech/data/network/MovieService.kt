package com.cinetech.data.network

import com.cinetech.data.network.model.ApiLoadMoviesResponse
import com.cinetech.data.network.model.ApiSearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("/v1.4/movie")
    suspend fun loadMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("selectFields") selectFields: List<String> = arrayListOf("id", "name", "alternativeName", "year", "ageRating", "countries", "poster", "rating")
    ): ApiLoadMoviesResponse

    @GET("/v1.4/movie/search")
    suspend fun searchMoviesByName(
        @Query("query") name: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ApiSearchMovieResponse

}