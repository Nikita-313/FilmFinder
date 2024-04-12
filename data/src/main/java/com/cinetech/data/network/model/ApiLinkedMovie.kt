package com.cinetech.data.network.model

import com.cinetech.domain.models.LinkedMovie

data class ApiLinkedMovie(
    val id: Long,
    val name: String? = null,
    val year: Int? = null,
    val poster: ApiPoster? = null,
    val rating: ApiRating? = null,
)

internal fun ApiLinkedMovie.toDomainLinkedMovie(): LinkedMovie {
    return LinkedMovie(
        id = id,
        name = name,
        year = year,
        preViewUrl = poster?.previewUrl,
        kpRating = rating?.kp,
    )
}