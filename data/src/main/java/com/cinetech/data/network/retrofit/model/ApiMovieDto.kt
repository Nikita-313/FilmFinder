package com.cinetech.data.network.retrofit.model

import com.cinetech.domain.models.MovieDto

data class ApiMovieDto(
    val id: Long,
    val name: String,
    val enName: String?,
    val year: Int,
    val country: String?,
    val ageRating: Int,
    val previewUrl: String?
)

internal fun ApiMovieDto.toDomainMovieDto(): MovieDto {
    return MovieDto(
        id = id,
        name = name,
        enName = enName,
        year = year,
        country = country,
        ageRating = ageRating,
        previewUrl = previewUrl,
    )
}