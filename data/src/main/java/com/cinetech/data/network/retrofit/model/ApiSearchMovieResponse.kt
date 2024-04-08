package com.cinetech.data.network.retrofit.model

import com.cinetech.domain.models.SearchMovieResponse as DomainSearchMovieResponse

data class ApiSearchMovieResponse(
    val docs: List<ApiMovieDto>,
    val total: Int,
    val limit: Int,
    val page:Int,
    val pages:Int,
)

fun ApiSearchMovieResponse.toDomainSearchMovieResponse():DomainSearchMovieResponse{
    return DomainSearchMovieResponse(
        docs = docs.map { it.toDomainMovieDto()},
        total = total,
        limit = limit,
        page = page,
        pages = pages,
    )
}