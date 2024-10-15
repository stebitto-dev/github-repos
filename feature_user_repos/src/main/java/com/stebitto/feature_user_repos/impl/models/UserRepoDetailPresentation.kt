package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDTO

internal data class UserRepoDetailPresentation(
    val id: Int,
    val name: String,
    val description: String,
    val language: String,
    val numberOfStars: Int
)

internal fun UserRepoDTO.toDetailPresentationModel(): UserRepoDetailPresentation {
    return UserRepoDetailPresentation(
        id = id,
        name = name,
        description = description,
        language = language,
        numberOfStars = numberOfStars
    )
}