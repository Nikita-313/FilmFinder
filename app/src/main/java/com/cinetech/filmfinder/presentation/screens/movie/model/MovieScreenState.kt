package com.cinetech.filmfinder.presentation.screens.movie.model

import com.cinetech.domain.models.CommentsResponse
import com.cinetech.domain.models.Movie
import com.cinetech.filmfinder.presentation.helpers.LoadableData

data class MovieScreenState(
    val movie: LoadableData<Movie>,
    val comments: LoadableData<CommentsResponse>,
)