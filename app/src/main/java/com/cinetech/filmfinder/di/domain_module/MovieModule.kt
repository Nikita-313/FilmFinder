package com.cinetech.filmfinder.di.domain_module

import com.cinetech.data.network.MovieService
import com.cinetech.data.repository.MovieRepositoryImp
import com.cinetech.domain.repository.MovieRepository
import com.cinetech.domain.usecase.LoadMovieByIdUseCase
import com.cinetech.domain.usecase.LoadMoviesUseCase
import com.cinetech.domain.usecase.LoadPossibleCountriesUseCase
import com.cinetech.domain.usecase.SearchMoviesByNameUseCase
import dagger.Module
import dagger.Provides

@Module
class MovieModule {
    @Provides
    fun providesMovieRepository(
        movieService: MovieService
    ): MovieRepository {
        return MovieRepositoryImp(movieService)
    }

    @Provides
    fun providesSearchMoviesByNameUseCase(movieRepository: MovieRepository): SearchMoviesByNameUseCase {
        return SearchMoviesByNameUseCase(movieRepository)
    }

    @Provides
    fun providesLoadMoviesUseCase(movieRepository: MovieRepository): LoadMoviesUseCase {
        return LoadMoviesUseCase(movieRepository)
    }

    @Provides
    fun providesLoadPossibleCountriesUseCase(movieRepository: MovieRepository): LoadPossibleCountriesUseCase {
        return LoadPossibleCountriesUseCase(movieRepository)
    }

    @Provides
    fun providesLoadMovieByIdUseCase(movieRepository: MovieRepository): LoadMovieByIdUseCase {
        return LoadMovieByIdUseCase(movieRepository)
    }
}