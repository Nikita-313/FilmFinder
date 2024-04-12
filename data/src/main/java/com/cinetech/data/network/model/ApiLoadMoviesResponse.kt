package com.cinetech.data.network.model

import com.cinetech.domain.models.LoadMoviesResponse


data class ApiLoadMoviesResponse(
    val docs: List<ApiMovieDto>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int,
)

fun ApiLoadMoviesResponse.toDomainLoadMovieResponse(): LoadMoviesResponse {
    return LoadMoviesResponse(
        docs = docs.map { it.toDomainPreviewMovie() },
        total = total,
        limit = limit,
        page = page,
        pages = pages,
    )
}