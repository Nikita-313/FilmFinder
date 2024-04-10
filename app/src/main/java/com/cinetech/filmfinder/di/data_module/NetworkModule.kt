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

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val builder: Request.Builder = originalRequest.newBuilder().header(
                "X-API-KEY",
                ""
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
            .baseUrl("https://api.kinopoisk.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

}