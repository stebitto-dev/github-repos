package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDTO

internal data class UserRepoRemote(
    val id: Int?,
    val name: String?,
    val full_name: String?,
    val description: String?,
    val language: String?,
    val stargazers_count: Int?
) {
    fun toUserRepoDTO(): UserRepoDTO {
        return UserRepoDTO(
            id = id ?: -1,
            name = name ?: "",
            fullName = full_name ?: "",
            description = description ?: "",
            language = language ?: "",
            numberOfStars = stargazers_count ?: 0
        )
    }
}