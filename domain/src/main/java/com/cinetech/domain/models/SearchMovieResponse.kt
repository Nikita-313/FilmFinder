package com.cinetech.domain.models

data class SearchMovieResponse(
    val docs: List<SearchMovieDto>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int,
)
