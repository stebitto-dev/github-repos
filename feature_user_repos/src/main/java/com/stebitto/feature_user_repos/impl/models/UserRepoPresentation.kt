package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDTO

internal data class UserRepoPresentation(
    val id: Int,
    val name: String,
    val owner: String,
    val description: String,
    val language: String,
    val numberOfStars: Int
)

internal fun UserRepoDTO.toPresentationModel(): UserRepoPresentation {
    return UserRepoPresentation(
        id = id,
        name = name,
        owner = owner,
        description = description,
        language = language,
        numberOfStars = numberOfStars
    )
}
