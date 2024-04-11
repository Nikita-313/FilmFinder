package com.cinetech.domain.models

data class LoadMovieResponse(
    val docs: List<PreviewMovie>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int,
)