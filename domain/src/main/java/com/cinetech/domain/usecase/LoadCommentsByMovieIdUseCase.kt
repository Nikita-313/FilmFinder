package com.cinetech.domain.usecase

import com.cinetech.domain.models.CommentsResponse
import com.cinetech.domain.models.LoadCommentsParam
import com.cinetech.domain.repository.CommentsRepository

class LoadCommentsByMovieIdUseCase(private val commentsRepository: CommentsRepository) {
    suspend fun execute(param: LoadCommentsParam): CommentsResponse {
        return commentsRepository.loadCommentsByMovieIdUseCase(param)
    }
}
