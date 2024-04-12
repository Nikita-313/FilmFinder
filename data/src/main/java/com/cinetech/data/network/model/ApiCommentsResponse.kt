package com.cinetech.data.network.model

import com.cinetech.domain.models.CommentsResponse

data class ApiCommentsResponse(
    val docs: List<ApiComment>,
    val total: Int,
    val limit: Int,
    val page: Int,
    val pages: Int,
)

fun ApiCommentsResponse.toDomainCommentsResponse(): CommentsResponse {
    return CommentsResponse(
        docs = docs.map { it.toDomainComment() },
        total = total,
        limit = limit,
        page = page,
        pages = pages,
    )
}