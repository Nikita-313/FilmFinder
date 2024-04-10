package com.cinetech.data.network.model

import com.cinetech.domain.models.PreviewMovie

data class ApiMovieDto(
    val id: Long?,
    val name: String?,
    val alternativeName: String?,
    val year: Int?,
    val ageRating: Int?,
    val countries: List<ApiCountry>,
    val poster: ApiPoster?,
    val rating: ApiRating?,
)


internal fun ApiMovieDto.toDomainPreviewMovie(): PreviewMovie {
    return PreviewMovie(
        id = id,
        name = name,
        alternativeName = alternativeName,
        year = year,
        ageRating = ageRating,
        countries = countries.map { it.name },
        preViewUrl = poster?.previewUrl,
        kpRating = rating?.kp,
    )
}