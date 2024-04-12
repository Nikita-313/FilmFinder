package com.cinetech.filmfinder.di.domain_module

import com.cinetech.data.network.MovieService
import com.cinetech.data.repository.CommentsRepositoryImp
import com.cinetech.domain.repository.CommentsRepository
import com.cinetech.domain.usecase.LoadCommentsByMovieIdUseCase
import dagger.Module
import dagger.Provides

@Module
class CommentModule {
    @Provides

    fun providesCommentsRepository(
        movieService: MovieService
    ): CommentsRepository {
        return CommentsRepositoryImp(movieService)
    }

    @Provides
    fun providesLoadCommentsByMovieIdUseCase(commentsRepository: CommentsRepository): LoadCommentsByMovieIdUseCase {
        return LoadCommentsByMovieIdUseCase(commentsRepository)
    }

}