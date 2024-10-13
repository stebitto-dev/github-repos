package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDTO

internal data class UserRepoRemote(
    val name: String,
    val description: String,
    val html_url: String
) {
    fun toUserRepoDTO(): UserRepoDTO {
        return UserRepoDTO(
            name = name,
            description = description
        )
    }
}