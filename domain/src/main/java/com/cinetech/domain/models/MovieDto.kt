package com.cinetech.domain.models

data class MovieDto(
    val id: Long,
    val name: String,
    val enName: String?,
    val year: Int,
    val country:String?,
    val ageRating:Int,
)