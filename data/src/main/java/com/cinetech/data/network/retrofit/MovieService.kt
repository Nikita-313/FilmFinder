package com.cinetech.data.network.retrofit

import com.cinetech.data.network.retrofit.model.ApiSearchMovieResponse
import retrofit2.http.GET


interface MovieService {
    @GET("/v1.4/movie?rating.imdb=8-10")
    suspend fun searchMoviesByName(): ApiSearchMovieResponse
}