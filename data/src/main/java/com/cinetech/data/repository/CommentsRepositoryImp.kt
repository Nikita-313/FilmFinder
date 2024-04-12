package com.cinetech.data.repository

import com.cinetech.data.network.MovieService
import com.cinetech.data.network.model.toDomainCommentsResponse
import com.cinetech.domain.models.CommentsResponse
import com.cinetech.domain.models.LoadCommentsParam
import com.cinetech.domain.repository.CommentsRepository

class CommentsRepositoryImp(private val movieService: MovieService) : CommentsRepository {

    override suspend fun loadCommentsByMovieIdUseCase(param: LoadCommentsParam): CommentsResponse {
        return movieService.loadCommentsByMovieId(
            movieId = param.movieId,
            page = param.page,
            limit = param.limitNumber
        ).toDomainCommentsResponse()
    }

}