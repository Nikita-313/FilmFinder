package com.cinetech.data.network.model

import com.cinetech.domain.models.PossibleValue

data class ApiPossibleValueDto(
    val name: String?,
    val slug: String?
)

fun ApiPossibleValueDto.toDomainPossibleValue(): PossibleValue {
    return PossibleValue(
        name = name,
        slug = slug,
    )
}