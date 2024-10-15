package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDTO

internal data class UserRepoPresentation(
    val id: Int,
    val name: String,
    val fullName: String,
    val description: String,
    val language: String,
    val numberOfStars: Int
)

internal fun UserRepoDTO.toPresentationModel(): UserRepoPresentation {
    return UserRepoPresentation(
        id = id,
        name = name,
        fullName = fullName,
        description = description,
        language = language,
        numberOfStars = numberOfStars
    )
}
