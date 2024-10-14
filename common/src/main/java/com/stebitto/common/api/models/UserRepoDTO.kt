package com.stebitto.common.api.models

data class UserRepoDTO(
    val id: Int,
    val name: String,
    val description: String,
    val language: String,
    val numberOfStars: Int
)