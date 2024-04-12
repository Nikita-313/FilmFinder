package com.cinetech.data.network.model

import com.cinetech.domain.models.Movie
import com.cinetech.domain.models.PreviewMovie

data class ApiMovieDto(
    val id: Long?,
    val name: String?,
    val alternativeName: String?,
    val year: Int?,
    val ageRating: Int?,
    val countries: List<ApiCountry>?,
    val poster: ApiPoster?,
    val rating: ApiRating?,
    val shortDescription: String?,
    val description: String?,
    val similarMovies: List<ApiLinkedMovie>?,
    val votes: ApiVotes,
)


internal fun ApiMovieDto.toDomainPreviewMovie(): PreviewMovie {
    return PreviewMovie(
        id = id,
        name = name,
        alternativeName = alternativeName,
        year = year,
        ageRating = ageRating,
        countries = countries?.map { it.name } ?: emptyList(),
        preViewUrl = poster?.previewUrl,
        kpRating = rating?.kp,
    )
}

internal fun ApiMovieDto.toDomainMovie(): Movie {
    return Movie(
        id = id,
        name = name,
        year = year,
        ageRating = ageRating,
        countries = countries?.map { it.name },
        posterUrl = poster?.url,
        kpRating = rating?.kp,
        shortDescription = shortDescription,
        description = description,
        similarMovies = similarMovies?.map { it.toDomainLinkedMovie() },
        kpVotesNumber = votes.kp?.toIntOrNull()
    )
}