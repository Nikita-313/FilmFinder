package com.cinetech.filmfinder.di

import com.cinetech.filmfinder.di.data_module.DataModule
import com.cinetech.filmfinder.di.domain_module.DomainModule
import com.cinetech.filmfinder.presentation.screens.movie_search.MovieSearchFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(mainActivity: MovieSearchFragment)
}