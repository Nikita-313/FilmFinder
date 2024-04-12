package com.cinetech.data.network.model

import com.cinetech.domain.models.SearchMovieDto

data class ApiSearchMovieDto(
    val id: Long,
    val name: String,
    val enName: String?,
    val year: Int,
    val country: String?,
    val ageRating: Int,
    val poster: ApiPoster,
    val rating: ApiRating?,
)

internal fun ApiSearchMovieDto.toDomainSearchMovieDto(): SearchMovieDto {
    return SearchMovieDto(
        id = id,
        name = name,
        enName = enName,
        year = year,
        country = country,
        ageRating = ageRating,
        previewUrl = poster.previewUrl,
        kpRating = rating?.kp
    )
}