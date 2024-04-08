package com.cinetech.filmfinder.di.domain_module

import com.cinetech.data.network.retrofit.MovieService
import com.cinetech.data.reposytory.MovieRepositoryImp
import com.cinetech.domain.repository.MovieRepository
import com.cinetech.domain.usecase.SearchMoviesByNameUseCase
import dagger.Module
import dagger.Provides

@Module
class MovieModule {
    @Provides
    fun providesMovieRepository(
        movieService: MovieService
    ):MovieRepository{
        return MovieRepositoryImp(movieService)
    }

    @Provides
    fun providesSearchMoviesByNameUseCase(movieRepository: MovieRepository): SearchMoviesByNameUseCase {
        return SearchMoviesByNameUseCase(movieRepository)
    }
}