package com.cinetech.data.network.retrofit

import com.cinetech.data.network.retrofit.model.ApiSearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {
    @GET("/v1.4/movie/search")
    suspend fun searchMoviesByName(
        @Query("query") name: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ApiSearchMovieResponse

}