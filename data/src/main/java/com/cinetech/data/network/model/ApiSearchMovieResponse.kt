package com.cinetech.data.network.model

import com.cinetech.domain.models.SearchMovieResponse as DomainSearchMovieResponse

data class ApiSearchMovieResponse(
    val docs: List<ApiSearchMovieDto>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int,
)

fun ApiSearchMovieResponse.toDomainSearchMovieResponse(): DomainSearchMovieResponse {
    return DomainSearchMovieResponse(
        docs = docs.map { it.toDomainSearchMovieDto() },
        total = total,
        limit = limit,
        page = page,
        pages = pages,
    )
}