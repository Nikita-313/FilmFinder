package com.cinetech.data.network.model

import com.cinetech.domain.models.LoadMovieResponse


data class ApiLoadMoviesResponse(
    val docs: List<ApiMovieDto>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int,
)

fun ApiLoadMoviesResponse.toDomainLoadMovieResponse(): LoadMovieResponse {
    return LoadMovieResponse(
        docs = docs.map { it.toDomainPreviewMovie() },
        total = total,
        limit = limit,
        page = page,
        pages = pages,
    )
}