package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDTO

internal data class UserRepoRemote(
    val name: String?,
    val description: String?,
    val language: String?,
    val stargazers_count: Int?
) {
    fun toUserRepoDTO(): UserRepoDTO {
        return UserRepoDTO(
            name = name ?: "",
            description = description ?: "",
            language = language ?: "",
            numberOfStars = stargazers_count ?: 0
        )
    }
}