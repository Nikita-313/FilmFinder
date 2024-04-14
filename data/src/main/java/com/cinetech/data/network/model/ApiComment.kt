package com.cinetech.data.network.model

import com.cinetech.domain.models.Comment

data class ApiComment(
    val id: Int,
    val title: String?,
    val review: String?,
    val date: String?,
    val author: String?
)

fun ApiComment.toDomainComment(): Comment {
    return Comment(
        id = id,
        title = title,
        review = review,
        date = date,
        author = author
    )
}