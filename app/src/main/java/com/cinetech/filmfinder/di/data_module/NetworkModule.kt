package com.cinetech.filmfinder.di.data_module

import com.cinetech.data.network.MovieService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {
    companion object{
        private const val API_KEY_NAME = "X-API-KEY"
        //Write token here:
        private const val API_KEY = ""
        private const val API_BASE_URL = "https://api.kinopoisk.dev"
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val builder: Request.Builder = originalRequest.newBuilder().header(
                API_KEY_NAME,
                API_KEY
            )
            val newRequest: Request = builder.build()
            chain.proceed(newRequest)
        }.build()
    }

    @Provides
    fun providesRetrofitMovieService(
        client: OkHttpClient
    ): MovieService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

}